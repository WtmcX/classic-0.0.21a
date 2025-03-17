
# Minecraft c0.0.21a TeaVM Port

This project is a port of **Minecraft c0.0.21a** to the browser using [TeaVM](https://teavm.org/), allowing users to experience this early version of Minecraft directly from a web browser.

## Features

- Full functionality of Minecraft c0.0.21a in a browser environment.
- Smooth integration using TeaVM for Java-to-JavaScript transpilation.
- No plugins or additional installations required—just open the web page and play!

## Requirements

- A modern web browser with JavaScript enabled.
- TeaVM for building the project (already configured in the repository).

## How to Run

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/minecraft-teavm-port.git
   cd minecraft-teavm-port
   ```

2. Build the project using TeaVM:
   ```bash
   ./gradlew build
   ```

3. Serve the generated files using a local server:
   ```bash
   python -m http.server 8080
   ```

4. Open your browser and navigate to:
   ```
   http://localhost:8080/
   ```

## Development

### Prerequisites

- Java Development Kit (JDK) 8 or later
- Gradle build tool
- TeaVM (already included as a dependency)

### Building the Project

To rebuild the project and generate the web files:
```bash
./gradlew teavm
```

### Directory Structure

- `src/main/java`: Source code for Minecraft c0.0.21a.
- `build/teavm`: Output directory containing the transpiled JavaScript and web resources.

## Credits

- Original Minecraft code by **Mojang**.
- Ported to the browser using **TeaVM** by [EymenWSMC].

## License

This project is for educational and personal use only. Minecraft and all related assets are owned by **Mojang Studios**. This port does not claim ownership or rights to the original game.

---
Enjoy playing Minecraft c0.0.21a in your browser!
```
