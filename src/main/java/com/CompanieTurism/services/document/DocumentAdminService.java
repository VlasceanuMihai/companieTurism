package com.CompanieTurism.services.document;

import com.CompanieTurism.dao.DocumentDao;
import com.CompanieTurism.exceptions.DocumentNotFoundException;
import com.CompanieTurism.exceptions.EmployeeNotFoundException;
import com.CompanieTurism.models.Document;
import com.CompanieTurism.models.Employee;
import com.CompanieTurism.repository.DocumentRepository;
import com.CompanieTurism.repository.EmployeeRepository;
import com.CompanieTurism.requests.document.DocumentRequest;
import com.CompanieTurism.responses.document.DocumentResponse;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.PersistenceException;
import java.util.List;

@Service
@Slf4j
public class DocumentAdminService {

    private final DocumentService documentService;
    private final DocumentRepository documentRepository;
    private final EmployeeRepository employeeRepository;
    private final DocumentDao documentDao;

    @Autowired
    public DocumentAdminService(DocumentService documentService,
                                DocumentRepository documentRepository,
                                EmployeeRepository employeeRepository,
                                DocumentDao documentDao) {
        this.documentService = documentService;
        this.documentRepository = documentRepository;
        this.employeeRepository = employeeRepository;
        this.documentDao = documentDao;
    }

    public List<DocumentResponse> getDocumentsByEmployeeAndDocumentName(Pageable pageable) {
        return this.documentRepository.findAllByEmployeeAndDocumentName(pageable);
    }

    @Transactional
    @SneakyThrows
    public DocumentResponse createDocument(DocumentRequest request) {
        String cnp = request.getCnp();

        Employee employee = this.employeeRepository.findByCnp(cnp)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee with id " + cnp + " not found!"));

        Document document = this.documentRepository.save(this.getUpdatedDocument(employee, request));

        return DocumentResponse.builder()
                .documentName(document.getDocumentName())
                .employeeFirstName(employee.getFirstName())
                .employeeLastName(employee.getLastName())
                .build();
    }

    private Document getUpdatedDocument(Employee employee, DocumentRequest documentRequest) {
        Document document = new Document();
        document.setEmployee(employee);
        document.setDocumentName(documentRequest.getDocumentName());
        document.setPath(documentRequest.getPath());
        return document;
    }

    @Transactional
    public DocumentResponse updateDocument(Integer documentId, DocumentRequest documentRequest) {
        if (!this.documentService.checkExistingId(documentId)) {
            log.info("Document with id {} not found.", documentId);
            throw new EmployeeNotFoundException("Document with id " + documentId + " not found!");
        }
        int res = this.documentRepository.updateDocument(documentId, documentRequest.getDocumentName(), documentRequest.getPath());

        if (res < 1) {
            throw new PersistenceException("Cannot update document with id: " + documentId);
        }
        log.info("Document with id {} has been updated with payload {}", documentId, documentRequest);

        Document document = this.documentRepository.findById(documentId)
                .orElseThrow(() -> new EmployeeNotFoundException("Document with id " + documentId + " not found!"));

        return DocumentResponse.builder()
                .documentName(documentRequest.getDocumentName())
                .employeeFirstName(document.getEmployee().getFirstName())
                .employeeLastName(document.getEmployee().getLastName())
                .build();
    }

    @Transactional
    @SneakyThrows
    public void deleteDocument(Integer documentId) {
        if (!this.documentService.checkExistingId(documentId)) {
            log.info("Document with id {} not found.", documentId);
            throw new DocumentNotFoundException("Document with id " + documentId + " not found!");
        }

        this.documentDao.delete(documentId);
        log.info("Document with id {} has been deleted!", documentId);

    }
}