package org.isep.eigenflow.ui;


import javafx.scene.control.ChoiceDialog;
import org.isep.eigenflow.domain.Personel;

import java.util.List;
import java.util.Optional;

public class MemberSelectionDialog extends ChoiceDialog<String> {

    public MemberSelectionDialog(List<Personel> allPersonnel) {
        super(null, allPersonnel.stream().map(Personel::getName).toList());
        setTitle("Select a Team Member");
        setHeaderText("Choose a member to add to the project.");
        setContentText("Available members:");
    }

    public Optional<String> showDialog() {
        return this.showAndWait();
    }
}
