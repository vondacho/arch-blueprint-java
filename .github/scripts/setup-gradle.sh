#!/bin/bash

set -euo pipefail
IFS=$'\n\t'

cat <<EOF >> gradle-local.properties

org.ajoberstar.grgit.auth.username=$GITHUB_USERNAME
org.ajoberstar.grgit.auth.password=$GITHUB_TOKEN

EOF
