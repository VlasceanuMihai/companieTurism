package com.CompanieTurism.services;

import com.CompanieTurism.models.Document;
import com.CompanieTurism.repository.DocumentRepository;
import com.CompanieTurism.responses.DocumenteResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class DocumentService {

    private final DocumentRepository documentRepository;

    @Autowired
    public DocumentService(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    public List<DocumenteResponse> getDocumentsByAngajatAndNumeDestiantie(Pageable pageable) {
        return this.documentRepository.findAllByAngajatAndNumeDocument(pageable);
    }
}
