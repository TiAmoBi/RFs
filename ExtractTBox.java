package com.tju.suma.test;

import com.clarkparsia.owlapi.modularity.locality.SemanticLocalityEvaluator;
import org.openrdf.model.Model;
import org.openrdf.model.impl.LinkedHashModel;
import org.openrdf.rio.*;
import org.openrdf.rio.helpers.StatementCollector;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.FileDocumentSource;
import org.semanticweb.owlapi.io.OWLOntologyDocumentSource;
import org.semanticweb.owlapi.io.StreamDocumentSource;
import org.semanticweb.owlapi.model.*;
//import org.semanticweb.owlapi.modularity.locality.LocalityClass;
//import org.semanticweb.owlapi.modularity.locality.SemanticLocalityModuleExtractor;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerConfiguration;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapi.reasoner.structural.StructuralReasonerFactory;
import org.semanticweb.rulewerk.core.model.api.Fact;
import org.semanticweb.rulewerk.core.reasoner.KnowledgeBase;
import org.semanticweb.rulewerk.rdf.RdfModelConverter;
import uk.ac.manchester.cs.owlapi.modularity.ModuleType;
import uk.ac.manchester.cs.owlapi.modularity.SyntacticLocalityModuleExtractor;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Stream;

public class ExtractTBox413 {
    protected static OWLOntologyManager owlOntologyManager = OWLManager.createOWLOntologyManager();
    protected static OWLOntology T = null;
    protected static OWLOntology A = null;
    protected static String path = "G:\\KB\\Database\\2018VlogData\\output\\lubm1\\lubm1_ontology.owl";
    protected static String pathA = "G:\\KB\\Database\\2018VlogData\\output\\lubm1\\lubm1.ttl";
    protected static String outpath = "G:\\KB\\Database\\KBQA\\lubm1\\subOrganizationOf.owl";
    protected static String pathABox = "G:\\KB\\Database\\KBQA\\lubm1\\subOrganizationOf.nt";
    protected static Iterator<RDFFormat> formatsToTry = Arrays
            .asList(RDFFormat.NTRIPLES, RDFFormat.TURTLE, RDFFormat.RDFXML).iterator();
    static {
        try {
//            OWLOntologyLoaderConfiguration loadingConfig = new OWLOntologyLoaderConfiguration();
//            loadingConfig = loadingConfig.setMissingImportHandlingStrategy(MissingImportHandlingStrategy.SILENT);
            T= owlOntologyManager.loadOntologyFromOntologyDocument(new File(path));
//            T = owlOntologyManager.loadOntologyFromOntologyDocument(new File(path));
            A = owlOntologyManager.loadOntologyFromOntologyDocument(new File(pathA));
        } catch (OWLOntologyCreationException e) {
            e.printStackTrace();
        }
    }

    public static Model parseRdfFromStream(final BufferedReader inputStream, final RDFFormat rdfFormat, final String baseIri)
            throws RDFParseException, RDFHandlerException, IOException {
        final Model model = new LinkedHashModel();
        final RDFParser rdfParser = Rio.createParser(rdfFormat);
        rdfParser.setRDFHandler(new StatementCollector(model));
        rdfParser.parse(inputStream, baseIri);
        return model;
    }

    public static List<String> addProcesoor() throws IOException {
        KnowledgeBase knowledgeBase = new KnowledgeBase();

        String rdfTriplePredicate = RdfModelConverter.RDF_TRIPLE_PREDICATE_NAME;
        Model model = null;
        while (model == null && formatsToTry.hasNext()) {
            final RDFFormat rdfFormat = formatsToTry.next();
            try {
                FileInputStream fl = new FileInputStream(pathABox);
                final InputStreamReader inputStreamReader = new InputStreamReader(fl);
                final BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String baseIri = new File(pathABox).toURI().toString();
                model = parseRdfFromStream(bufferedReader, rdfFormat, baseIri);

                System.out.println("Found RDF document in format " + rdfFormat.getName() + " ...\n");
            } catch (RDFHandlerException | RDFParseException e) {
                System.out.println("Failed to parse as " + rdfFormat.getName() + ": " + e.getMessage());
            }
        }
        RdfModelConverter rdfModelConverter = new RdfModelConverter(true, rdfTriplePredicate);
        rdfModelConverter.addAll(knowledgeBase, model);
        List<Fact> list1 = knowledgeBase.getFacts();
        List<String> list2 = new ArrayList<>();
        int in = 0;
        for (Fact f :list1
        ) {
            String s1 = f.getArguments().get(1).toString();
            String s3 = f.getArguments().get(2).toString();
            int n = s1.indexOf(">");
            int x = s3.indexOf(">");
            String s2 = s1.substring(1,n);
            String s4 = s3.substring(1,x);
            if(!(list2.contains(s2))){

                list2.add(s2);
                System.out.println(s2);
                in++;
            }
            if(!(list2.contains(s4))){

                list2.add(s4);
                System.out.println(s4);
                in++;
            }
        }
        System.out.println("递归谓词:"+in);
        return list2;
    }
    public static void extract(List<String> list) throws OWLOntologyCreationException, OWLOntologyStorageException, FileNotFoundException {
        Stream<OWLAxiom> s = T.axioms();
        OWLReasonerFactory owlReasonerf = new StructuralReasonerFactory();
        System.out.println("TBox 公理:"+T.getLogicalAxiomCount());
        SyntacticLocalityModuleExtractor syntacticLocalityModuleExtractor = new SyntacticLocalityModuleExtractor(owlOntologyManager,s, ModuleType.TOP);
//        SemanticLocalityModuleExtractor see = new SemanticLocalityModuleExtractor(LocalityClass.BOTTOM,s,owlOntologyManager,owlReasonerf);
        Set<OWLEntity> set = new HashSet<>();
        Set<OWLEntity> sete = A.getSignature();
        int i = 0;
        System.out.println("TBox 实体:"+sete.size());
        for (OWLEntity oee: sete
        ) {
//            System.out.println(oee.getIRI().toString());
            if(list.contains(oee.getIRI().toString())){
                set.add(oee);
//                System.out.println(oee);
                i++;
            }
        }
        System.out.println("相同实体:"+i);
        Stream<OWLEntity> stream = set.stream();
        Set<OWLAxiom> owlAxiomSet = syntacticLocalityModuleExtractor.extract(set);

//        for (OWLAxiom oe: owlAxiomSet
//             ) {
//            System.out.println(oe);
//            n++;
//        }
        System.out.println("TBox抽取公理:"+owlAxiomSet.size());
        owlOntologyManager.createOntology(owlAxiomSet).saveOntology(new FileOutputStream(outpath));

    }

    public static void main(String[] args) throws IOException, OWLOntologyCreationException, OWLOntologyStorageException {
        ExtractTBox413.extract(ExtractTBox413.addProcesoor());
    }
}
