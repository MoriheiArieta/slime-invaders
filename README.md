# Slime Invaders: 2D Shooter
- Made with Java and JavaFX

<p align="center">
  <img src="https://github.com/user-attachments/assets/63b8a9b9-100a-4a9f-80a7-060815a8897f" width = "240" height = "300">
</p>
  

## How to run:
- **Make sure to have Java SE 21 or lower versions and JavaFX installed**

### Locally IDE:
- Clone repository or download files
- Add this line to the VM Args:
```
--module-path "<JavaFX/lib path>" --add-modules javafx.controls,javafx.graphics,javafx.fxml
```
- add JavaFX to classpath
- run

### Through runnable .jar file:
- can be downloaded [HERE](https://drive.google.com/file/d/1esINmZQYA-6gaVS8rmJvXtYTfJA9o7Ww/view?usp=sharing)
- open a terminal then navigate to path where SlimeInvaders.jar file is located
- enter this line:
```
"java --module-path "<JavaFX/lib path>" --add-modules javafx.controls,javafx.graphics,javafx.fxml -jar SlimeInvaders.jar"
```

### Some Notes:
- custom fonts doesn't apply on the executable .jar file (haven't fixed this yet)
