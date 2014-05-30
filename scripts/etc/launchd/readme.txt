MaritimeCloud server start-on-boot solution:

- customize MaritimeCloudServer.restart.plist
- copy file MaritimeCloudServer.restart.plist to /System/Library/LaunchAgents
- load once with: sudo launchctl load -w /System/Library/LaunchAgents/MaritimeCloudServer.restart.plist
