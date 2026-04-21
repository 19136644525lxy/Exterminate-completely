# Exterminate Completely

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

## 项目介绍

Exterminate Completely 是一个 Minecraft Forge 模组，用于在杀死敌对生物后防止其再次生成。

## 功能特性

- **自动黑名单**：杀死的敌对生物会自动添加到黑名单，防止再次生成
- **白名单系统**：可以通过指令添加生物到白名单，允许其继续生成
- **多语言支持**：支持英文和中文界面
- **世界独立配置**：每个世界有独立的黑名单配置
- **生物映射表**：支持生物ID与中文名称的映射

## 安装方法

1. 确保已安装 Minecraft Forge 1.19.2
2. 下载 `ExterminateCompletely-0.1.2.jar` 文件
3. 将文件放入 `.minecraft/mods` 文件夹
4. 启动游戏

## 使用指令

### 主指令：`/ec`

#### 白名单管理
- `/ec whitelist` - 查看白名单中的生物
- `/ec whitelist add <生物ID或中文名称>` - 添加生物到白名单
  - 例如：`/ec whitelist add minecraft:pig` 或 `/ec whitelist add 猪`

#### 黑名单管理
- `/ec blacklist` - 查看当前世界的黑名单生物

#### 语言设置
- `/ec language` - 查看当前语言和可用语言
- `/ec language <语言代码>` - 切换语言
  - 语言代码：`en_us` (英文), `zh_cn` (中文)

## 配置文件

- **白名单**：`config/ec-whitelist.toml`
- **黑名单**：每个世界的 `ec/blacklist.txt` 文件
- **映射表**：`config/ec/mapp/<语言代码>/mappings.txt`

## 支持的生物

- 苦力怕 (Creeper)
- 僵尸 (Zombie)
- 骷髅 (Skeleton)
- 蜘蛛 (Spider)
- 末影人 (Enderman)
- 史莱姆 (Slime)
- 女巫 (Witch)
- 幻翼 (Phantom)
- 溺尸 (Drowned)
- 尸壳 (Husk)
- 流浪者 (Stray)
- 洞穴蜘蛛 (Cave Spider)
- 蠹虫 (Silverfish)
- 烈焰人 (Blaze)
- 恶魂 (Ghast)
- 岩浆怪 (Magma Cube)
- 僵尸猪灵 (Zombified Piglin)
- 猪灵 (Piglin)
- 猪灵蛮兵 (Piglin Brute)
- 疣猪兽 (Hoglin)
- 僵尸疣猪兽 (Zoglin)
- 凋灵骷髅 (Wither Skeleton)
- 守卫者 (Guardian)
- 远古守卫者 (Elder Guardian)
- 潜影贝 (Shulker)
- 末影螨 (Endermite)
- 唤魔者 (Evoker)
- 卫道士 (Vindicator)
- vex (Vex)
- 掠夺者 (Pillager)
- 劫掠兽 (Ravager)
- 监守者 (Warden)

## 技术信息

- **Minecraft 版本**：1.19.2
- **Forge 版本**：43.4.13+
- **Java 版本**：17
- **模组 ID**：ec
- **版本**：0.1.2

## 开发者信息

- **作者**：yifei, xiaomingle
- **GitHub**：[链接](https://github.com/19136644525lxy/Exterminate-completely)

## 许可协议

此项目采用 MIT 许可协议。详见 [LICENSE](LICENSE) 文件。

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
