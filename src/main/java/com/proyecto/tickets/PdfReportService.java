package com.proyecto.tickets;

import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;
import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class PdfReportService {

    public byte[] generate(List<Ticket> tickets) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Document doc = new Document();
        PdfWriter.getInstance(doc, out);
        doc.open();
        doc.add(new Paragraph("Sistema de tickets - Reporte"));
        doc.add(new Paragraph("Total de tickets: " + tickets.size()));
        doc.add(new Paragraph(" "));
        for (Ticket t : tickets) {
            doc.add(new Paragraph("#" + t.getNumeroSolicitud() + " | " + t.getDescripcionBreve()
                    + " | Urgencia: " + t.getUrgencia()
                    + " | Estado: " + t.getEstado()
                    + " | Asignado a: " + (t.getAsignadoA() == null ? "-" : t.getAsignadoA())));
        }
        doc.close();
        return out.toByteArray();
    }
}
