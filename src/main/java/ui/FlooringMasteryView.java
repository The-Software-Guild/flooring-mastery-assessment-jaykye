package ui;

public class FlooringMasteryView {
    UserIO io;

    public FlooringMasteryView() {
        UserIO io = new UserIOConsoleImpl();
    }

    public FlooringMasteryView(UserIO io) {
        this.io = io;
    }

    public void displayMainMenu() {
        int menuNum = 1;
        io.print("  * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
        io.print(menuNum++ + ". Display Orders");
        io.print(menuNum++ + ". Add an Order");
        io.print(menuNum++ + ". Edit an Order");
        io.print(menuNum++ + ". Remove an Order");
        io.print(menuNum++ + ". Export All Data");
        io.print(menuNum++ + ". Quit");

        io.print("  * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
    }
}
