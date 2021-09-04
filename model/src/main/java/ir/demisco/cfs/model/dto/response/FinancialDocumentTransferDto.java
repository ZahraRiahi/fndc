package ir.demisco.cfs.model.dto.response;

import java.util.Date;
import java.util.List;

public class FinancialDocumentTransferDto {

    private Long id;
    private List<Long> financialDocumentItemId;
    private Long transferType;
    private Date date;
    private String documentNumber;
}
