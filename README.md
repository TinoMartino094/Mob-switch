# Mob Switch

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

A server-side Minecraft Fabric mod that implements a "Mob Switch" using Inhibitor Cores. This mod allows players to prevent natural monster spawning in specific chunks, perfect for protecting bases or technical builds.

## Current Features

- **Inhibitor Cores**: Specialized Heavy Core blocks that act as mob spawning suppressors.
- **Selective Blocking**: Only blocks `MONSTER` category spawns that are `NATURAL` or `STRUCTURE` based. Passive mobs, player-triggered spawns (like spawners), and other categories remain unaffected.
- **Server-Side Only**: Does not require any client-side installation. Vanilla clients can connect and play without issues.
- **Piston Resistant**: Inhibitor Cores are unpushable by pistons to prevent accidental (or intentional) displacement of your mob switch.
- **Persistent State**: Core positions are saved in the world data, ensuring the switch remains active across server restarts.

## How to Use

1. **Obtain an Inhibitor Core**: You can craft an **Inhibitor Core** using a normal Heavy Core and 4 Torches in a crafting table.
2. **Place the Core**: Place the Inhibitor Core in the chunk you wish to protect. Natural monster spawning will be disabled within that chunk.
3. **Removal**: Simply mine the Inhibitor Core to resume normal mob spawning in the chunk.

## AI-Assisted Development Note

This mod was developed with the assistance of AI. If you encounter any bugs, please report them via the GitHub issue tracker.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
