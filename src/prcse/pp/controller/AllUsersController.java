package prcse.pp.controller;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.util.Callback;
import javafx.util.Duration;
import prcse.pp.model.Person;
import prcse.pp.model.Tenant;
import prcse.pp.model.UserList;
import prcse.pp.view.NoteCell;
import prcse.pp.view.PaymentCell;
import prcse.pp.view.UserCell;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ResourceBundle;


/**
 * FXML Controller class
 *
 * @author PRCSE
 */
public class AllUsersController implements Initializable, ControlledScreen {

    /******************************************************
     *     FXML VARIABLES - RELATIVE TO UserDetails.XML
     ******************************************************/
    @FXML // fx:id="btnUserSearch"
    private Button btnUserSearch;
    @FXML // fx:id="btn_create_user"
    private Button btn_create_user;
    @FXML // fx:id="img_create_user"
    private ImageView img_create_user;
    @FXML // fx:id="img_view_users"
    private ImageView img_view_users;
    @FXML // fx:id="btn_view_user"
    private Button btn_view_user;
    @FXML // fx:id="Draggable"
    private BorderPane draggable;
    @FXML // fx:id="closeBtn"
    private Button closeBtn;
    @FXML // fx:id="maximBtn"
    private Button maximBtn;
    @FXML // fx:id="minimBtn"
    private Button minimBtn;
    @FXML // fx:id="nav1"
    private AnchorPane nav1;
    @FXML // fx:id="nav2"
    private AnchorPane nav2;
    @FXML // fx:id="nav3"
    private AnchorPane nav3;
    @FXML // fx:id="nav4"
    private AnchorPane nav4;
    @FXML // fx:id="nav5"
    private AnchorPane nav5;
    @FXML // fx:id="nav6"
    private AnchorPane nav6;
    @FXML // fx:id="accent1"
    private Rectangle accent1;
    @FXML // fx:id="accent2"
    private Rectangle accent2;
    @FXML // fx:id="accent3"
    private Rectangle accent3;
    @FXML // fx:id="accent4"
    private Rectangle accent4;
    @FXML // fx:id="accent5"
    private Rectangle accent5;
    @FXML // fx:id="accent6"
    private Rectangle accent6;
    @FXML //fx:id="nav_icon1"
    private Pane nav_icon1;
    @FXML //fx:id="nav_bg1"
    private Button nav_bg1;
    @FXML //fx:id="nav_icon2"
    private Pane nav_icon2;
    @FXML //fx:id="nav_bg2"
    private Button nav_bg2;
    @FXML //fx:id="nav_icon3"
    private Pane nav_icon3;
    @FXML //fx:id="nav_bg3"
    private Button nav_bg3;
    @FXML //fx:id="nav_icon4"
    private Pane nav_icon4;
    @FXML //fx:id="nav_bg4"
    private Button nav_bg4;
    @FXML //fx:id="nav_icon5"
    private Pane nav_icon5;
    @FXML //fx:id="nav_bg5"
    private Button nav_bg5;
    @FXML //fx:id="nav_icon6"
    private Pane nav_icon6;
    @FXML //fx:id="nav_bg6"
    private Button nav_bg6;
    @FXML // fx:id="title"
    private Label title;
    @FXML // fx:id="spinner_green"
    private ImageView spinner_green;
    @FXML // fx:id="txtUser_Username"
    private TextField txtUsers_Username;
    @FXML // fx:id="searchBar"
    private Pane searchBar;
    @FXML // fx:id="searchButtons"
    private Pane searchButtons;
    @FXML // fx:id="searchWrap"
    private Pane searchWrap;
    @FXML // fx:id="txtName"
    private TextField txtName;
    @FXML // fx:id="txtEmail"
    private TextField txtEmail;
    @FXML // fx:id="txtProperty"
    private TextField txtProperty;
    @FXML // fx:id="lstUsers"
    private ListView lstUsers;
    @FXML // fx:id="widget_top_left"
    private Pane widget_top_left;
    @FXML // fx:id="widget_top_right"
    private Pane widget_top_right;
    @FXML // fx:id="widget_bottom_right"
    private Pane widget_bottom_right;
    @FXML // fx:id="body"
    private AnchorPane body;


    // Set variables to allow for draggable window.
    private Tenant thisTenant = ScreensFramework.searchObj.getTenant();
    private UserList userList = ScreensFramework.tenants;
    private UserList userResult = new UserList();
    private Boolean objectsSet = false;
    private double xOffset = 0;
    private double yOffset = 0;
    ScreensController myController;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle resources)
    {
        // Set opacity of widgets
        widget_top_left.setOpacity(0.3);
        widget_top_right.setOpacity(0.3);
        widget_bottom_right.setOpacity(0.3);

        // Animate the scene in
        body.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                animateIn();
                resetStyles();

                if(objectsSet == false)
                {
                    userResult = ScreensFramework.searchObj.getSearchedUsers();
                    displayUsers();
                }

                objectsSet = true;
            }
        });

        // Set the display graphic for title
        Effect glow = new Glow(0.3);
        title.setEffect(glow);

        // Set the draggable component
        draggable.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        draggable.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                draggable.getScene().getWindow().setX(event.getScreenX() - xOffset);
                draggable.getScene().getWindow().setY(event.getScreenY() - yOffset);
            }
        });

        nav1.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                accent1.setStyle("visibility: visible");
            }
        });
        nav1.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                accent1.setStyle("visibility: hidden");
            }
        });
        nav4.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                accent4.setStyle("visibility: visible");
            }
        });
        nav4.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                accent4.setStyle("visibility: hidden");
            }
        });
        nav3.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                accent3.setStyle("visibility: visible");
            }
        });
        nav3.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                accent3.setStyle("visibility: hidden");
            }
        });
        nav5.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                accent5.setStyle("visibility: visible");
            }
        });
        nav5.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                accent5.setStyle("visibility: hidden");
            }
        });
        nav6.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                accent6.setStyle("visibility: visible");
            }
        });
        nav6.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                accent6.setStyle("visibility: hidden");
            }
        });

        /******************************************************
         *              MODEL MANIPULATION METHODS
         ******************************************************/
        // Reset the textbox to "Enter a users name..." if the box is empty on focus out.
        txtUsers_Username.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {
                resetText(txtUsers_Username, newPropertyValue);
            }
        });
        txtName.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {
                resetText(txtName, newPropertyValue);
            }
        });
        txtEmail.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {
                resetText(txtEmail, newPropertyValue);
            }
        });
        txtProperty.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {
                resetText(txtProperty, newPropertyValue);
            }
        });


        btnUserSearch.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                btnUserSearch.getStyleClass().add("searching");
                spinner_green.setVisible(true);
            }
        });

        // Utility controls
        closeBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Platform.exit();
            }
        });

        searchWrap.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                nav_bg2.getStyleClass().add("light_hover");
                accent2.setStyle("visibility: visible");
            }
        });
        searchWrap.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                nav_bg2.getStyleClass().remove("light_hover");
                accent2.setStyle("visibility: hidden");
                hideUsers();
            }
        });

    }

    /******************************************************
     *                  UI ELEMENT LOAD
     ******************************************************/
    public UserList getResults()
    {
        return ScreensFramework.searchObj.getSearchedUsers();
    }

    public void displayUsers()
    {
        // Array list of users to be added to the ListView
        ObservableList users = FXCollections.observableArrayList();

        if(userResult.size() > 0)
        {
            System.out.println("hi");
            // Loop through each user in the system and create a ListView item
            for(int i = 0; i < userResult.size(); i++)
            {
                thisTenant = userResult.getUserAt(i);

                // Check whether an address line 2 is specified
                if(thisTenant.getAddr_line_2() == "NULL" || thisTenant.getAddr_line_2() == null)
                    users.add(thisTenant.getName() + "\n" + thisTenant.getAddr_line_1());
                else
                    users.add(thisTenant.getName() + "\n" + thisTenant.getAddr_line_1() + " " + thisTenant.getAddr_line_2());
            }
        } else {
            for(int i = 0; i < userList.size(); i++)
            {
                thisTenant = userList.getUserAt(i);

                // Check whether an address line 2 is specified
                if(thisTenant.getAddr_line_2() == "NULL" || thisTenant.getAddr_line_2() == null)
                    users.add(thisTenant.getName() + "\n" + thisTenant.getAddr_line_1());
                else
                    users.add(thisTenant.getName() + "\n" + thisTenant.getAddr_line_1() + " " + thisTenant.getAddr_line_2());
            }
        }

        // Use a Cell Factory to format the output.
        lstUsers.setFixedCellSize(80);
        lstUsers.setCellFactory(new Callback<ListView, ListCell>() {
            @Override
            public ListCell call(ListView listView) {
                UserCell c = new UserCell();
                c.getStyleClass().add("border-bottom");
                return c;
            }
        });

        // Populate the listview
        lstUsers.setItems(users);
    }



    /******************************************************
     *                ANIMATION CONTROLS
     ******************************************************/
    public void showUsers()
    {
        final Timeline slideOut = new Timeline();
        slideOut.setCycleCount(1);
        slideOut.setAutoReverse(false);

        // Slide out the window
        final KeyValue kv1 = new KeyValue(searchBar.translateXProperty(), 339);
        final KeyFrame kf1 = new KeyFrame(Duration.millis(300), kv1);
        final KeyValue kv2 = new KeyValue(searchButtons.translateXProperty(), 339);
        final KeyFrame kf2 = new KeyFrame(Duration.millis(300), kv2);
        final KeyValue kv3 = new KeyValue(searchButtons.translateYProperty(), 67);
        final KeyFrame kf3 = new KeyFrame(Duration.millis(700), kv3);

        // Fade the widgets
        final KeyValue kv4 = new KeyValue(widget_top_right.opacityProperty(), 0.3);
        final KeyFrame kf4 = new KeyFrame(Duration.millis(500), kv4);
        final KeyValue kv5 = new KeyValue(widget_top_left.opacityProperty(), 0.3);
        final KeyFrame kf5 = new KeyFrame(Duration.millis(500), kv5);
        final KeyValue kv6 = new KeyValue(widget_bottom_right.opacityProperty(), 0.3);
        final KeyFrame kf6 = new KeyFrame(Duration.millis(500), kv6);

        slideOut.getKeyFrames().addAll(kf1, kf2, kf3, kf4, kf5, kf6);
        slideOut.play();

        //txtUsers_Username.requestFocus();
    }

    // Hide the slide out user widget
    public void hideUsers()
    {
        final Timeline slideBack = new Timeline();
        slideBack.setCycleCount(1);
        slideBack.setAutoReverse(false);
        final KeyValue kv1 = new KeyValue(searchBar.translateXProperty(), 0);
        final KeyFrame kf1 = new KeyFrame(Duration.millis(500), kv1);
        final KeyValue kv2 = new KeyValue(searchButtons.translateXProperty(), 0);
        final KeyFrame kf2 = new KeyFrame(Duration.millis(500), kv2);
        final KeyValue kv3 = new KeyValue(searchButtons.translateYProperty(), 0);
        final KeyFrame kf3 = new KeyFrame(Duration.millis(300), kv3);

        // Fade widgets back in
        final KeyValue kv4 = new KeyValue(widget_top_right.opacityProperty(), 1);
        final KeyFrame kf4 = new KeyFrame(Duration.millis(500), kv4);
        final KeyValue kv5 = new KeyValue(widget_top_left.opacityProperty(), 1);
        final KeyFrame kf5 = new KeyFrame(Duration.millis(500), kv5);
        final KeyValue kv6 = new KeyValue(widget_bottom_right.opacityProperty(), 1);
        final KeyFrame kf6 = new KeyFrame(Duration.millis(500), kv6);

        slideBack.getKeyFrames().addAll(kf1, kf2, kf3, kf4, kf5, kf6);
        slideBack.play();

        txtUsers_Username.setText("");
        spinner_green.setVisible(false);
        btnUserSearch.getStyleClass().remove("searching");
    }

    public void resetText(TextField txt, Boolean newPropertyValue)
    {
        if (newPropertyValue) {
            System.out.println("Textfield on focus");
        } else {
            System.out.println("Textfield out focus");
            String username = txt.getText();
            if(username.trim().isEmpty())
            {
                txt.setText("");
            }
        }
    }

    public void animateIn()
    {
        final Timeline load_scene = new Timeline();
        load_scene.setCycleCount(1);
        load_scene.setAutoReverse(false);
        final KeyValue kv0 = new KeyValue(title.layoutYProperty(), 20);
        final KeyFrame kf0 = new KeyFrame(Duration.millis(250), kv0);
        final KeyValue kv1 = new KeyValue(widget_top_right.opacityProperty(), 1);
        final KeyFrame kf1 = new KeyFrame(Duration.millis(500), kv1);
        final KeyValue kv2 = new KeyValue(widget_top_left.opacityProperty(), 1);
        final KeyFrame kf2 = new KeyFrame(Duration.millis(500), kv2);
        final KeyValue kv3 = new KeyValue(widget_bottom_right.opacityProperty(), 1);
        final KeyFrame kf3 = new KeyFrame(Duration.millis(500), kv3);

        // Animate the position in
        final KeyValue kv4 = new KeyValue(widget_top_right.translateXProperty(), -320);
        final KeyFrame kf4 = new KeyFrame(Duration.millis(400), kv4);
        final KeyValue kv5 = new KeyValue(widget_top_left.translateXProperty(), 880);
        final KeyFrame kf5 = new KeyFrame(Duration.millis(500), kv5);
        final KeyValue kv6 = new KeyValue(widget_bottom_right.translateYProperty(), -483);
        final KeyFrame kf6 = new KeyFrame(Duration.millis(500), kv6);

        // Build the animation
        load_scene.getKeyFrames().addAll(kf0, kf1, kf2, kf3, kf4, kf5, kf6);
        load_scene.play();
    }

    public void animateOut()
    {
        final Timeline load_scene = new Timeline();
        load_scene.setCycleCount(1);
        load_scene.setAutoReverse(false);
        final KeyValue kv0 = new KeyValue(title.layoutYProperty(), -100);
        final KeyFrame kf0 = new KeyFrame(Duration.millis(250), kv0);
        final KeyValue kv1 = new KeyValue(widget_top_right.opacityProperty(), 0.3);
        final KeyFrame kf1 = new KeyFrame(Duration.millis(500), kv1);
        final KeyValue kv2 = new KeyValue(widget_top_left.opacityProperty(), 0.3);
        final KeyFrame kf2 = new KeyFrame(Duration.millis(500), kv2);
        final KeyValue kv3 = new KeyValue(widget_bottom_right.opacityProperty(), 0.3);
        final KeyFrame kf3 = new KeyFrame(Duration.millis(500), kv3);

        // Animate the position in
        final KeyValue kv4 = new KeyValue(widget_top_right.translateXProperty(), 0);
        final KeyFrame kf4 = new KeyFrame(Duration.millis(400), kv4);
        final KeyValue kv5 = new KeyValue(widget_top_left.translateXProperty(), 0);
        final KeyFrame kf5 = new KeyFrame(Duration.millis(500), kv5);
        final KeyValue kv6 = new KeyValue(widget_bottom_right.translateYProperty(), 0);
        final KeyFrame kf6 = new KeyFrame(Duration.millis(500), kv6);

        // Build the animation
        load_scene.getKeyFrames().addAll(kf0, kf1, kf2, kf3, kf4, kf5, kf6);
        load_scene.play();
    }

    /**
     * Animates the scene out on a new Thread to allow the animation to play through without being
     * interrupted by the main thread, styles are applied to show the new active button
     */
    private void nextForm(final String ID)
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    clearStyles();
                    switch(ID) {
                        case "Dashboard":
                            nav_bg1.getStyleClass().addAll("active");
                            nav_icon1.getStyleClass().add("active");
                            accent1.getStyleClass().addAll("active", "show");
                            break;
                        case "Tenant":
                            nav_bg2.getStyleClass().addAll("active");
                            nav_icon2.getStyleClass().add("active");
                            accent2.getStyleClass().addAll("active", "show");
                            break;
                        case "Properties":
                            nav_bg3.getStyleClass().addAll("active");
                            nav_icon3.getStyleClass().add("active");
                            accent3.getStyleClass().addAll("active", "show");
                            break;
                        case "Payments":
                            nav_bg4.getStyleClass().addAll("active");
                            nav_icon4.getStyleClass().add("active");
                            accent4.getStyleClass().addAll("active", "show");
                            break;
                        case "Messages":
                            nav_bg5.getStyleClass().addAll("active");
                            nav_icon5.getStyleClass().add("active");
                            accent5.getStyleClass().addAll("active", "show");
                            break;
                        case "Settings":
                            nav_bg6.getStyleClass().addAll("active");
                            nav_icon6.getStyleClass().add("active");
                            accent6.getStyleClass().addAll("active", "show");
                            break;
                    }

                    // Animate the scene
                    animateOut();
                    Thread.sleep(300);
                } catch(Exception e )
                {
                    System.out.println("There was an error handling the animation...");
                }
                // Go to our view.
                myController.setScreen(ID);
            }
        }).start();

        objectsSet = false;
    }

    /**
     * Clears the styles on the current button
     */
    private void clearStyles()
    {
        // Active state for this window
        nav_icon2.getStyleClass().remove("active");
        nav_bg2.getStyleClass().remove("active");
        accent2.getStyleClass().remove("show");
    }

    /**
     * Reset the navigation styles to make this current window the active one, if we don't call this method
     * then the next time we load this window form the HashMap, the wrong active state shall be applied
     */
    private void resetStyles()
    {
        // Active state for this window
        nav_icon2.getStyleClass().add("active");
        nav_bg2.getStyleClass().add("active");
        accent2.getStyleClass().addAll("active", "show");

        // Default styles for every other nav element
        nav_icon1.getStyleClass().remove("active");
        accent1.getStyleClass().removeAll("active", "show");
        nav_bg1.getStyleClass().remove("active");
        nav_icon4.getStyleClass().remove("active");
        accent4.getStyleClass().removeAll("active", "show");
        nav_bg4.getStyleClass().remove("active");
        nav_icon3.getStyleClass().remove("active");
        accent3.getStyleClass().removeAll("active", "show");
        nav_bg3.getStyleClass().remove("active");
        nav_icon5.getStyleClass().remove("active");
        accent5.getStyleClass().removeAll("active", "show");
        nav_bg5.getStyleClass().remove("active");
        nav_icon6.getStyleClass().remove("active");
        accent6.getStyleClass().removeAll("active", "show");
        nav_bg6.getStyleClass().remove("active");

    }

    // Set the parent of the new screen
    public void setScreenParent(ScreensController screenParent){
        myController = screenParent;
    }

    // Navigation Control
    @FXML
    private void goToDashboard(ActionEvent event){
        // If the user panel is open then hide it
        hideUsers();
        nextForm(ScreensFramework.screen1ID);
    }
    @FXML
    private void goToUsers(ActionEvent event){
        hideUsers();
        nextForm(ScreensFramework.screen2ID);
    }
    @FXML
    private void goToProperties(ActionEvent event){
        hideUsers();
        nextForm(ScreensFramework.screen3ID);
    }
    @FXML
    private void goToPayments(ActionEvent event){
        hideUsers();
        nextForm(ScreensFramework.screen4ID);
    }
    @FXML
    private void goToMessages(ActionEvent event){
        hideUsers();
        nextForm(ScreensFramework.screen5ID);
    }
    @FXML
    private void goToSettings(ActionEvent event){
        hideUsers();
        nextForm(ScreensFramework.screen6ID);
    }
    @FXML
    private void goToAddUser(ActionEvent event){
        hideUsers();
        nextForm(ScreensFramework.screen7ID);
    }
    @FXML
    private void goToAllUsers(ActionEvent event){
        hideUsers();
        nextForm(ScreensFramework.screen8ID);
    }
}

