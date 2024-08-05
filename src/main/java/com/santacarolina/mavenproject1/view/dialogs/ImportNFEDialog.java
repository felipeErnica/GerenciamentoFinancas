package com.santacarolina.mavenproject1.view.dialogs;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.santacarolina.mavenproject1.dto.NfeDTO;
import com.santacarolina.mavenproject1.model.FiscalDocument;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;

public class ImportNFEDialog extends JDialog{

    JFileChooser fileChooser;

    public ImportNFEDialog() {
        initComponents();
    }

    private void initComponents() {

        fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Arquivo XML (*.xml)","xml"));
        fileChooser.showOpenDialog(this);

        File fileXML = fileChooser.getSelectedFile();

        try {
            XmlMapper mapper = new XmlMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
            NfeDTO nfeDTO = mapper.readValue(fileXML, NfeDTO.class);
            System.out.println(nfeDTO);
            FiscalDocument nfe = new FiscalDocument(nfeDTO);
            DocDialog docDialog = new DocDialog(nfe);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}

