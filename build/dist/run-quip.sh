#!/bin/sh
java --module-path "$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )/lib" --add-modules javafx.controls,javafx.fxml,javafx.graphics -jar quip.jar