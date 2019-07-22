# BMod

BMod is a mod for the popular minecraft video game. It is designed to be
ran on Sponge, which is an application programming interface allowing easy bindings
to obfuscated minecraft server code.

The Bangor League Gaming Society (BUGL) is a society at Bangor University that
require a way for players to 'claim' part of the level they play in.
Although there are current products on the market, they lack needed features and
are undocumented. BMod aims to resolve these issues.

In addition to fulfilling the criteria for the society, BMod's secondary
aim is to example a fully documented system using modern Java tools. This includes
fully commented source code and supporting text documents explaining the structure
and algorithms used.

[Environment setup and development guidelines](#Contributing)

---

- [BMod](#bmod)
	- [Why Minecraft](#why-minecraft)
	- [Functionality](#functionality)
		- [Summery](#summery)
		- [Common Server commands](#common-server-commands)
		- [Chat Formatting](#chat-formatting)
		- [Land Claiming and Teams](#land-claiming-and-teams)
	- [User Interface](#user-interface)
	- [Technical](#technical)
		- [Problems with Modifications](#problems-with-modifications)
		- [Storage](#storage)
	- [Contributing](#contributing)
		- [Environment Setup](#environment-setup)
		- [Creating Issues](#creating-issues)
		- [Pull Requests](#pull-requests)

---

## Why Minecraft

Although minecraft is a video game, it provides large aspects crucial to modern
application development:

- Real-Time execution
	- All code must be quick and efficient, otherwise server performance will degrade.
- Storage Solutions
	- Including databases, the system will need to store persistent data.
- Full Configuration
	- All features must be configurable, and support real-time configuration changes.
- Multithreading
	- Multithreaded services include processing and I/O.
- Resource limits
	- Minecraft servers commonly have a limited amount of resources that must be
		shared. Any mod must be as efficient as possible.
- Working with existing APIs
- User Interface and Experience
	- Although the interface will be text, this may include hover-overs, command
		layout and system administrator functions. Standard user experience requirements
		still apply.
- Open source development and tools
	- Although the original mod will be developed without a team, the project must
		be able to support contributors after completion, therefore tools such as
		Git must be used for suitable version control.

## Functionality

### Summery

The mod needs to provide features in three key areas:

- Common server commands
- Chat formatting
- Land claiming and Teams

Every aspect of all three areas needs to be configurable.

### Common Server commands

Minecraft has a large amount of plugins to perform the same common commands,
these are notably a mix of player control (banning, kicking, warning players)
and movement commands (set homes, teleport to other players, defined locations)

### Chat Formatting

The chat is one of the most seen aspect of minecraft and is essential in communicating
information to the player. It is also a way to talk to other players on the server.

Formatting the chat will include:

- Color support
- Censored words (racial slurs, insults, etc)
- Player metadata (hover-over)
- Prefixed Team name (with metadata)
- Extension: Command highlighting
- Extension: Word metadata

### Land Claiming and Teams

There are two distinct types of minecraft player, ones who wish to only fight against
the environment (PvE) and ones who prefer player vs player combat (PvP).

The servers BUGL run must accommodate for both types of player, and thus must provide
a way for selectable player and land protection. An issue is raised due to the modded
nature of the minecraft instance;- it is not possible to hard-code the interactions
due to dynamic damage causes and interaction effects.

## User Interface

The main and only way for the player to communicate to the mod is by using the
in-game chat interface. Here they can input commands and see output, much like a
standard terminal.

However, minecraft supports metadata, and this can provide hover-over information
to the player, or the ability to run commands when clicked.

Another aspect to keep in mind is the nature of the game world, it is possible to
manipulate and edit the world the player is in, such as displaying particle effects.

## Technical

Minecraft is a game based on Java, and its source is obfuscated. However, de-obfuscation
mappings have been made by the community. This has resulted in a central modding
platform called [Minecraft Forge](https://github.com/MinecraftForge).

[Sponge](https://github.com/SpongePowered) is a project created for server-side
mods (called plugins) that change the way the minecraft server works, this is
server-side *only*, and therefore supports non-modded clients.

RMod is based on the Sponge API, and will therefore work with vanilla and modded
clients. This adds the restriction that we cannot create any client-side effects
that are not exposed already.

This is the environment the application will be executing in;- as a sponge plugin
on a server that may-or-may-not be modified. It is unable to directly effect clients
and may have constraints such as limited memory, processing power, or no database
functionality.

### Problems with Modifications

Since the modified nature of the clients and server cannot be guaranteed, it is
possible for the client to perform actions unknown to vanilla. Since BMod must offer
some form of land and player protection, it must be ensured that these events must be
tested for.

One example would be the default mechanic for eating food. It has to be allowed
in claimed areas, otherwise players might starve. However;- some food may have
side effects such as randomly teleporting the player, and this must not be allowed
on protected territory (as such effects can effect players, or land).

### Storage

Storage must support databases, and an alternate file-based storage. This is due
to the restricted environment of minecraft servers.

---

## Contributing

### Environment Setup

For setting up the developing environment, a simple clone should do the trick.
then use any IDE you would like, but keep in mind that line endings are `CRLF`
and indentation format is tabs.

To build the plugin, use `.\gradlew.bat build` or `.\gradlew.sh build` depending on your OS

To build the plugin and then automatically launch the development server, first create the server using `.\newserver.bat`
and then `.\startserver.bat` will automatically build the plugin, move it to the server's plugin directory, and start.
Keep in mind that the batch file does not check for a successful build before starting the server.

### Creating Issues

If you find any bugs, or would like to request features, use the [Issue Tracker](https://github.com/WYVERN2742/Bmod/issues)
Please remember to explain the bug or feature in detail!

### Pull Requests

If you would like to contribute to the project, please keep in mind that any features must be within the scope of the project, and that there is currently no license attached to the project!
