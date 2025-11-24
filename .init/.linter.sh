#!/bin/bash
cd /home/kavia/workspace/code-generation/event-management-system-46029-46038/event_management_backend
./gradlew checkstyleMain
LINT_EXIT_CODE=$?
if [ $LINT_EXIT_CODE -ne 0 ]; then
   exit 1
fi

