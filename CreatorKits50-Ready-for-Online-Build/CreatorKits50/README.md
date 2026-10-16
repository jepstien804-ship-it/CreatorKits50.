# CreatorKits50 — Paper plugin source

Fan-made Minecraft SMP creator-inspired kits. These are original loadouts, not official kits or endorsements.

## Build the JAR
1. Install Java 21 and Apache Maven.
2. Open a terminal in this folder and run `mvn package`.
3. Copy `target/CreatorKits50-1.0.0.jar` into your Paper server's `plugins/` folder.
4. Restart the server.

## Commands
- `/kit list` — list kits and IDs
- `/kit <kitname>` — claim a kit
- Aliases: `/creatorkit`, `/creatorkits`

Edit `src/main/resources/config.yml` before building, or edit `plugins/CreatorKits50/config.yml` after first startup. Default cooldown is 30 seconds. Set `settings.kit-permission: true` to require `creatorkits.kit.<kitname>` permission.
