package com.santacarolina.areas.classificacao.frmManageClassificacao;

import java.util.List;

import com.santacarolina.dao.ClassificacaoDAO;
import com.santacarolina.dto.ClassificacaoDTO;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.interfaces.CustomTableModel;
import com.santacarolina.ui.CustomTableModelImpl;

/**
 * ClassificacaoTableModel
 */
public class ClassificacaoTableModel implements CustomTableModel<ClassificacaoDTO>{


    private final CustomTableModelImpl<ClassificacaoDTO> baseModel;
    private List<ClassificacaoDTO> list;

    private final String[] columnNames = {
        "Receita X Despesa",
        "Categoria Contábil",
        "Número da Classificação",
        "Classificação"
    };

    public ClassificacaoTableModel() throws FetchFailException {
        list = new ClassificacaoDAO().findAllDTO();
        this.baseModel = new CustomTableModelImpl<>(this, list);
    }

    @Override
    public CustomTableModelImpl<ClassificacaoDTO> getBaseModel() { return baseModel; }

    @Override
    public int getRowCount() { return list.size(); }

    @Override
    public int getColumnCount() { return columnNames.length; }

    @Override
    public String getColumnName(int columnIndex) { return columnNames[columnIndex]; }

    @Override
    public Class<?> getColumnClass(int columnIndex) { return String.class; }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) { return false; }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ClassificacaoDTO dto = list.get(rowIndex);
        return switch(columnIndex) {
            case 0 -> dto.getFluxoCaixa().toString();
            case 1 -> dto.getNomeCategoria();
            case 2 -> dto.getNumeroIdentificacao();
            case 3 -> dto.getNomeClassificacao();
            default -> throw new IllegalArgumentException("Unexpected value: " + columnIndex);
        };
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) { }

    @Override
    public ClassificacaoDTO getObject(int rowIndex) { return baseModel.getObject(rowIndex); }
    
}
