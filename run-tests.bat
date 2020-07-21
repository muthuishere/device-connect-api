rem lein auto with-profile dev test
rem lein auto with-profile dev test :only send-phone-api.sessions-test/test-remove-session-on-non-existent-session
rem lein auto with-profile dev test :only send-phone-api.api-handlers-test/test-current
rem lein auto with-profile dev test :only send-phone-api.websockets-test/test-current
lein auto with-profile dev test :only send-phone-api.socket.buffer-test
rem lein auto test :only send-phone-api.routes-integrationtest