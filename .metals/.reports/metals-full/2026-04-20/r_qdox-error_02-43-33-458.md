error id: file:///C:/Users/Antony/Documents/JuanDiego/src/main/java/com/Grifo/JuanDiego/service/PagoService.java
file:///C:/Users/Antony/Documents/JuanDiego/src/main/java/com/Grifo/JuanDiego/service/PagoService.java
### com.thoughtworks.qdox.parser.ParseException: syntax error @[70,33]

error in qdox parser
file content:
```java
offset: 3014
uri: file:///C:/Users/Antony/Documents/JuanDiego/src/main/java/com/Grifo/JuanDiego/service/PagoService.java
text:
```scala
package com.Grifo.JuanDiego.service;

import com.Grifo.JuanDiego.model.Pagos;
import com.Grifo.JuanDiego.model.Cobranza;
import com.Grifo.JuanDiego.repository.PagosRepository;
import com.Grifo.JuanDiego.repository.CobranzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.nio.file.*;
import java.util.UUID;

@Service
public class PagoService {

    @Autowired
    private PagosRepository pagosRepository;

    @Autowired
    private CobranzaRepository cobranzaRepository;

    // Carpeta donde se guardarán los vouchers físicamente
    private final String rootFolder = "uploads";

    @Transactional
    public Pagos registrarPago(Pagos pago, MultipartFile archivo) {
        // 1. GENERAR N° OPERACIÓN AUTOMÁTICO (Ej: PAG-A1B2C3)
        String codigoAuto = "PAG-" + UUID.randomUUID().toString().substring(0, 6).toUpperCase();
        pago.setNroOperacion(codigoAuto);

        // 2. Manejo del archivo (Voucher)
        if (archivo != null && !archivo.isEmpty()) {
            try {
                Files.createDirectories(Paths.get(rootFolder));
                String nombreArchivo = UUID.randomUUID().toString() + "_" + archivo.getOriginalFilename();
                Path path = Paths.get(rootFolder + "/" + nombreArchivo);
                Files.copy(archivo.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                pago.setComprobante(nombreArchivo);
            } catch (Exception e) {
                throw new RuntimeException("Error al guardar el voucher: " + e.getMessage());
            }
        }

        // 3. Buscamos la cobranza asociada
        Cobranza cobranza = cobranzaRepository.findById(pago.getCobranza().getIdCobranza())
                .orElseThrow(() -> new RuntimeException("No se encontró el registro de cobranza"));

        // 4. Liquidación Total
        // Como el monto viene del HTML (que es la deuda completa), el saldo será 0
        cobranza.setMontoPendiente(BigDecimal.ZERO);
        cobranza.setEstadoCobranza(Cobranza.EstadoCobranza.Pagado);

        // 5. Persistencia
        cobranzaRepository.save(cobranza);
        return pagosRepository.save(pago);
    }

        // 2. Buscamos la cobranza asociada
        Cobranza cobranza = cobranzaRepository.findById(pago.getCobranza().getIdCobranza())
                .orElseThrow(() -> new RuntimeException("No se encontró el registro de cobranza"));

        // 3. Lógica de saldos
        BigDecimal saldoAnterior = cobranza.getMontoPendiente() != null ? cobranza.getMontoPendiente() : BigDecimal.ZERO;
        BigDecimal montoPagado = pago.getMontoPagado() != null ? pago.getMontoPagado() : BigDecimal.ZERO;
        BigDecimal nuevoSaldo = saldoAnterior.subtract(montoPagado);

        // 4. Actualizamos el estado de la cobranza
        if (nuevoSaldo.compareTo(@@BigDecimal.ZERO) <= 0) {
            cobranza.setMontoPendiente(BigDecimal.ZERO);
            cobranza.setEstadoCobranza(Cobranza.EstadoCobranza.Pagado);
        } else {
            cobranza.setMontoPendiente(nuevoSaldo);
            // Si estaba vencido y pagó una parte, podrías decidir si sigue vencido o pasa a pendiente
        }

        // 5. Persistencia atómica
        cobranzaRepository.save(cobranza);
        return pagosRepository.save(pago);
    }
}
```

```



#### Error stacktrace:

```
com.thoughtworks.qdox.parser.impl.Parser.yyerror(Parser.java:2025)
	com.thoughtworks.qdox.parser.impl.Parser.yyparse(Parser.java:2147)
	com.thoughtworks.qdox.parser.impl.Parser.parse(Parser.java:2006)
	com.thoughtworks.qdox.library.SourceLibrary.parse(SourceLibrary.java:232)
	com.thoughtworks.qdox.library.SourceLibrary.parse(SourceLibrary.java:190)
	com.thoughtworks.qdox.library.SourceLibrary.addSource(SourceLibrary.java:94)
	com.thoughtworks.qdox.library.SourceLibrary.addSource(SourceLibrary.java:89)
	com.thoughtworks.qdox.library.SortedClassLibraryBuilder.addSource(SortedClassLibraryBuilder.java:162)
	com.thoughtworks.qdox.JavaProjectBuilder.addSource(JavaProjectBuilder.java:174)
	scala.meta.internal.mtags.JavaMtags.indexRoot(JavaMtags.scala:48)
	scala.meta.internal.mtags.MtagsIndexer.index(MtagsIndexer.scala:21)
	scala.meta.internal.mtags.MtagsIndexer.index$(MtagsIndexer.scala:20)
	scala.meta.internal.mtags.JavaMtags.index(JavaMtags.scala:38)
	scala.meta.internal.mtags.Mtags$.allToplevels(Mtags.scala:150)
	scala.meta.internal.metals.DefinitionProvider.fromMtags(DefinitionProvider.scala:359)
	scala.meta.internal.metals.DefinitionProvider.$anonfun$positionOccurrence$4(DefinitionProvider.scala:278)
	scala.Option.orElse(Option.scala:477)
	scala.meta.internal.metals.DefinitionProvider.$anonfun$positionOccurrence$1(DefinitionProvider.scala:278)
	scala.Option.flatMap(Option.scala:283)
	scala.meta.internal.metals.DefinitionProvider.positionOccurrence(DefinitionProvider.scala:270)
	scala.meta.internal.metals.JavaDocumentHighlightProvider.$anonfun$documentHighlight$1(JavaDocumentHighlightProvider.scala:26)
	scala.collection.immutable.List.map(List.scala:247)
	scala.meta.internal.metals.JavaDocumentHighlightProvider.documentHighlight(JavaDocumentHighlightProvider.scala:22)
	scala.meta.internal.metals.MetalsLspService.$anonfun$documentHighlights$1(MetalsLspService.scala:993)
	scala.meta.internal.metals.CancelTokens$.$anonfun$apply$2(CancelTokens.scala:26)
	scala.concurrent.Future$.$anonfun$apply$1(Future.scala:687)
	scala.concurrent.impl.Promise$Transformation.run(Promise.scala:467)
	java.base/java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1095)
	java.base/java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:619)
	java.base/java.lang.Thread.run(Thread.java:1447)
```
#### Short summary: 

QDox parse error in file:///C:/Users/Antony/Documents/JuanDiego/src/main/java/com/Grifo/JuanDiego/service/PagoService.java