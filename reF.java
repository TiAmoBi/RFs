package com.tju.suma.test;

import com.tju.suma.axiomProcessor.Processor;
//import org.eclipse.rdf4j.rio.*;
import org.openrdf.model.Model;
import org.openrdf.model.impl.LinkedHashModel;
import org.openrdf.rio.*;
import org.openrdf.rio.helpers.StatementCollector;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.model.parameters.Imports;
import org.semanticweb.rulewerk.core.model.api.Fact;
import org.semanticweb.rulewerk.core.reasoner.KnowledgeBase;
import org.semanticweb.rulewerk.rdf.RdfModelConverter;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class reF {
    protected static  OWLOntologyManager m = OWLManager.createOWLOntologyManager();
    protected static Imports INCLUDED ;
    protected static OWLOntology reSPO1 = null;
    protected static OWLOntology reSPO2 = null;

    protected static OWLOntology reABoxT = null;
    protected static OWLOntology reABox = null;
    protected static Iterator<OWLAxiom> reSPOIterator1;
    protected static Iterator<OWLAxiom> reSPOIterator2;
    protected static Iterator<OWLAxiom> reABoxTIterator;
    protected static Iterator<RDFFormat> formatsToTry = Arrays
            .asList(RDFFormat.NTRIPLES, RDFFormat.TURTLE, RDFFormat.RDFXML).iterator();

    protected static int index = 0;


    static {
        try {
            reSPO1 = m.createOntology();
            reSPO2 = m.createOntology();
            reABoxT = m.createOntology();
            reABox = m.createOntology();

        } catch (OWLOntologyCreationException e) {
            e.printStackTrace();
        }
    }



    public static void addProcesoor() throws OWLOntologyCreationException, IOException {
        String outpath = "VLogdata/vo_ABox_re.nt";
        String pathABox = "G:\\KB\\Database\\2018VlogData\\MVlog\\vo\\vo_ABox.owl";
        String outre = "G:\\KB\\Database\\KBQA\\vo\\";
        String outunre = "G:\\KB\\Database\\KBQA\\vo\\unrecu.nt";
        reABox = m.loadOntologyFromOntologyDocument(new File(pathABox));
        for (OWLAxiom o:reABox.getAxioms()
             ) {
            System.out.println(o);
        }
    }
//    }

    public static void main(String[] args) throws OWLOntologyCreationException, IOException {

        reF.addProcesoor();
    }
}


