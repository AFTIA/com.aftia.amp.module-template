package com.aftia.application.doccloud.samples.extractpdf;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.adobe.pdfservices.operation.auth.Credentials;
import com.adobe.pdfservices.operation.pdfops.options.extractpdf.ExtractElementType;
import com.adobe.pdfservices.operation.pdfops.options.extractpdf.ExtractPDFOptions;
import com.aftia.adobe.doccloud.core.authentication.DocAuthentication;
import com.aftia.adobe.doccloud.core.exceptions.DocAuthenticationException;
import com.aftia.adobe.karaf.extractpdf.ExtractPDFServices;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(property = { "alias=/doc-cloud/samples/textextractpdf", "servlet-name=Text Extract PDF" })
public class ExtractTextPDFServlet extends HttpServlet implements Servlet {

    @Reference
    public ExtractPDFServices extractPDFServices;

    @Override
    public void doPost(final HttpServletRequest req, final HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            final ParsedRequest parsedRequest = this.processRequest(req);

            ExtractPDFOptions extractPDFOptions = ExtractPDFOptions.extractPdfOptionsBuilder()
                    .addElementsToExtract(Arrays.asList(ExtractElementType.TEXT)).build();
            InputStream pdf = parsedRequest.files.get("pdf");
            ByteArrayOutputStream result = (ByteArrayOutputStream) this.extractPDFServices.extractTextFromPDF(pdf,
                    extractPDFOptions, new SimpleDocAuthentication(parsedRequest));

            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getOutputStream().write(result.toByteArray());
            resp.getOutputStream().flush();
            resp.getOutputStream().close();

        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            throw new ServletException(e.getMessage(), e);
        }
    }

    public ParsedRequest processRequest(final HttpServletRequest req) throws FileUploadException, IOException {
        Map<String, InputStream> files = new HashMap();
        Map<String, String> fields = new HashMap();
        // Create a factory for disk-based file items
        DiskFileItemFactory factory = new DiskFileItemFactory();

        // Configure a repository (to ensure a secure temp location is used)
        ServletContext servletContext = this.getServletConfig().getServletContext();
        File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
        factory.setRepository(repository);

        // Create a new file upload handler
        ServletFileUpload upload = new ServletFileUpload(factory);

        // Parse the request
        List<FileItem> items = upload.parseRequest(req);

        Iterator<FileItem> iter = items.iterator();
        while (iter.hasNext()) {
            FileItem item = iter.next();

            if (!item.isFormField()) {
                files.put(item.getFieldName(), item.getInputStream());
            } else {
                fields.put(item.getFieldName(), item.getString());
            }
        }

        ParsedRequest parsedRequest = new ParsedRequest();
        parsedRequest.fields = fields;
        parsedRequest.files = files;

        return parsedRequest;
    }

    public class ParsedRequest {
        public Map<String, InputStream> files;
        public Map<String, String> fields;
    }

    public class SimpleDocAuthentication implements DocAuthentication {
        private ParsedRequest parsedRequest;
        private String key;

        public SimpleDocAuthentication(ParsedRequest parsedRequest) throws IOException {
            this.parsedRequest = parsedRequest;
            this.key = IOUtils.toString(parsedRequest.files.get("key"), StandardCharsets.UTF_8);
        }

        public Credentials getCredentials() throws DocAuthenticationException {
            try {
                return Credentials.serviceAccountCredentialsBuilder()
                        .withAccountId(this.parsedRequest.fields.get("accountId"))
                        .withClientId(this.parsedRequest.fields.get("clientId"))
                        .withClientSecret(this.parsedRequest.fields.get("clientSecret"))
                        .withOrganizationId(this.parsedRequest.fields.get("organizationId")).withPrivateKey(key)
                        .build();
            } catch (Exception e) {
                throw new DocAuthenticationException(e.getMessage(), e);
            }

        }

    }
}
