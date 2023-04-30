#!/bin/bash

set -euo pipefail
IFS=$'\n\t'

cat <<EOF >> gradle-local.properties

githubUsername=$GITHUB_USERNAME
githubToken=$GITHUB_TOKEN

EOF
