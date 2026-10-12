#!/usr/bin/env bash
set -euo pipefail

usage() {
    printf 'Usage: bash run.sh [--build-only]\n'
}

fail() {
    printf 'Error: %s\n' "$*" >&2
    exit 1
}

if (( $# > 1 )); then
    usage >&2
    exit 2
fi

build_only=false
case "${1:-}" in
    '') ;;
    --build-only|-BuildOnly) build_only=true ;;
    -h|--help) usage; exit 0 ;;
    *) usage >&2; exit 2 ;;
esac

project_dir=$(cd -- "$(dirname -- "${BASH_SOURCE[0]}")" && pwd)

# Use JAVA_HOME when provided; otherwise find the JDK already on PATH.
if [[ -n "${JAVA_HOME:-}" ]]; then
    jdk_home=$JAVA_HOME
    if command -v cygpath >/dev/null 2>&1; then
        jdk_home=$(cygpath -u "$jdk_home")
    fi
else
    compiler=$(command -v javac || true)
    [[ -n "$compiler" ]] || fail 'A JDK version 11 or newer is required. Set JAVA_HOME to its installation folder or add its bin folder to PATH.'
    if command -v realpath >/dev/null 2>&1; then
        compiler=$(realpath "$compiler")
    fi
    jdk_home=$(cd -- "$(dirname -- "$compiler")/.." && pwd)
fi

javac="$jdk_home/bin/javac"
java="$jdk_home/bin/java"
[[ -x "$javac" && -x "$java" ]] || fail "No complete JDK found at '$jdk_home'. JAVA_HOME must point to the JDK folder, not its bin folder."
export JAVA_HOME="$jdk_home"

output_dir="$project_dir/bin"
sources=()
while IFS= read -r -d '' source; do
    sources+=("$source")
done < <(find "$project_dir/src" -type f -name '*.java' -print0)
(( ${#sources[@]} > 0 )) || fail "No Java source files found in '$project_dir/src'."

mkdir -p "$output_dir"
printf 'Using JDK: %s\n' "$jdk_home"
"$javac" --release 11 -encoding UTF-8 -d "$output_dir" "${sources[@]}"
printf 'Compiled %s Java source files to %s\n' "${#sources[@]}" "$output_dir"

if [[ "$build_only" == false ]]; then
    exec "$java" -cp "$output_dir" ap.main.MainGameOfLife
fi
