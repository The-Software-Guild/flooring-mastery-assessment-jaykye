package controller;

import dao.FlooringMasteryDao;
import dao.FlooringMasteryDaoFileImpl;
import ui.FlooringMasteryView;

public class FlooringMasteryController {
    FlooringMasteryDao dao;
    FlooringMasteryView view;

    public FlooringMasteryController() {
        FlooringMasteryDao dao = new FlooringMasteryDaoFileImpl();
        FlooringMasteryView view = new FlooringMasteryView();
    }

    public FlooringMasteryController(FlooringMasteryDao dao, FlooringMasteryView view) {
        this.dao = dao;
        this.view = view;
    }

    public void run() {

    }
}
