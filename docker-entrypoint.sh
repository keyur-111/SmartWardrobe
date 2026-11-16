#!/bin/sh
set -eu

port="${PORT:-8080}"
server_xml="${CATALINA_HOME}/conf/server.xml"

case "$port" in
    ''|*[!0-9]*)
        echo "PORT must be a numeric TCP port." >&2
        exit 1
        ;;
esac

if ! grep -Eq '<Connector port="[0-9]+"' "$server_xml"; then
    echo "Tomcat HTTP connector was not found in ${server_xml}." >&2
    exit 1
fi

sed -i -E "s/(<Connector port=\")[0-9]+/\\1${port}/" "$server_xml"
exec "$@"