package com.tju.suma.test;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.model.parameters.Imports;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;

public class getABox319 {

    protected static OWLOntologyManager m = OWLManager.createOWLOntologyManager();
    protected static OWLOntology re = null;
    protected static OWLOntology reABox = null;
    protected static OWLOntology reTBox = null;

    static {
        try {
            re = m.createOntology();
            reABox = m.createOntology();
            reTBox = m.createOntology();

        } catch (OWLOntologyCreationException e) {
            e.printStackTrace();
        }
    }

    public static void getABox() throws OWLOntologyCreationException, FileNotFoundException, OWLOntologyStorageException {
        String path = "G:\\KB\\Database\\2018VlogData\\output\\enm_2\\enm.xrdf";
        String outABoxpath = "G:\\KB\\Database\\2018VlogData\\output\\enm_2\\enm_2ABox.owl";
        String outTBoxpath = "G:\\KB\\Database\\2018VlogData\\output\\enm_2\\enm_2_TBox.owl";
        OutputStream outputABoxStream =new FileOutputStream(outABoxpath);
        OutputStream outputTBoxStream =new FileOutputStream(outTBoxpath);
        Set<OWLAxiom> setABox = new HashSet<>();
//        Set<OWLAxiom> setTBox = new HashSet<>();

        re = m.loadOntologyFromOntologyDocument(new File(path));
//        setABox = re.getABoxAxioms(EXCLUDED);
//        setTBox = re.getTBoxAxioms(EXCLUDED);
        Stream<OWLAxiom> streamABox = re.aboxAxioms(Imports.INCLUDED);
        System.out.println(streamABox.count());
        Stream<OWLAxiom> streamABox1 = re.aboxAxioms(Imports.INCLUDED);
        reABox.addAxioms(streamABox1);
//        for (OWLAxiom o:setABox
//             ) {
//            reABox.addAxiom(o);
//        }
        reABox.saveOntology(outputABoxStream);
        Stream<OWLAxiom> streamTBox1 = re.tboxAxioms(Imports.INCLUDED);
        reTBox.addAxioms(streamTBox1);
        reTBox.saveOntology(outputTBoxStream);
    }

    public static void main(String[] args) throws OWLOntologyCreationException, OWLOntologyStorageException, FileNotFoundException {
        getABox319.getABox();
    }
}
