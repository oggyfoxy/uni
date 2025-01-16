package org.isep.eigenflow.ui;


import javafx.scene.control.ChoiceDialog;
import org.isep.eigenflow.domain.Personel;

import java.util.List;

public class MemberSelectionDialog extends ChoiceDialog<String> {

    public MemberSelectionDialog(List<Personel> allPersonnel) {
        super(null, allPersonnel.stream().map(Personel::getName).toList());
        setTitle("Select a Team Member");
        setHeaderText("Choose a member to add to the project.");
        setContentText("Available members:");
    }

}
