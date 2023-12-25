package release.gameoflife;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class GameApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        // Загрузка FXML файла
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/release/gameoflife/game-view.fxml")));

        // Установка заголовка окна
        primaryStage.setTitle("Game of Life");

        // Создание новой сцены и установка ее на главную сцену
        primaryStage.setScene(new Scene(root, 665, 700));

        // Отображение главной сцены
        primaryStage.show();
    }

    public static void main(String[] args) {
        // Запуск приложения
        launch(args);
    }
}
