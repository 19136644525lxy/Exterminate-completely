# Exterminate Completely

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![GitHub](https://img.shields.io/badge/GitHub-Exterminate%20Completely-blue)](https://github.com/19136644525lxy/Exterminate-completely)
[![CurseForge](https://img.shields.io/badge/CurseForge-Exterminate%20Completely-orange)](https://www.curseforge.com/minecraft/mc-mods/exterminate-completely)
[![Modrinth](https://img.shields.io/badge/Modrinth-Exterminate%20Completely-blue)](https://modrinth.com/mod/exterminate-completely)

## Project Introduction

Exterminate Completely is a Minecraft Forge mod that prevents hostile mobs from respawning after being killed.

## Features

- **Auto Blacklist**: Killed hostile mobs are automatically added to the blacklist to prevent respawning
- **Whitelist System**: Add mobs to whitelist via commands to allow them to continue spawning
- **Multi-language Support**: English and Chinese interfaces
- **World-independent Configuration**: Each world has independent blacklist configuration
- **Mob Mapping**: Support for mapping between mob IDs and names

## Installation

1. Ensure Minecraft Forge 1.19.2 is installed
2. Download `ExterminateCompletely-0.1.2.jar` file
3. Place the file in `.minecraft/mods` folder
4. Start the game

## Commands

### Main Command: `/ec`

#### Whitelist Management
- `/ec whitelist` - View mobs in whitelist
- `/ec whitelist add <mob ID or name>` - Add mob to whitelist
  - Example: `/ec whitelist add minecraft:pig` or `/ec whitelist add Pig`

#### Blacklist Management
- `/ec blacklist` - View blacklist mobs in current world

#### Language Settings
- `/ec language` - View current language and available languages
- `/ec language <language code>` - Switch language
  - Language codes: `en_us` (English), `zh_cn` (Chinese)

## Configuration Files

- **Whitelist**: `config/ec-whitelist.toml`
- **Blacklist**: `ec/blacklist.txt` file in each world
- **Mapping**: `config/ec/mapp/<language code>/mappings.txt`

## Supported Mobs

- Creeper
- Zombie
- Skeleton
- Spider
- Enderman
- Slime
- Witch
- Phantom
- Drowned
- Husk
- Stray
- Cave Spider
- Silverfish
- Blaze
- Ghast
- Magma Cube
- Zombified Piglin
- Piglin
- Piglin Brute
- Hoglin
- Zoglin
- Wither Skeleton
- Guardian
- Elder Guardian
- Shulker
- Endermite
- Evoker
- Vindicator
- Vex
- Pillager
- Ravager
- Warden

## Technical Information

- **Minecraft Version**: 1.19.2
- **Forge Version**: 43.4.13+
- **Java Version**: 17
- **Mod ID**: ec
- **Version**: 0.1.2

## Developer Information

- **Authors**: Yifei, Xiaomingle
- **GitHub**: [Link](https://github.com/19136644525lxy/Exterminate-completely)

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

```
MIT License

Copyright (c) 2026 Exterminate Completely

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
