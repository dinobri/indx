package br.com.dinobri;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {
        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("br.com.dinobri");

        noClasses()
            .that()
            .resideInAnyPackage("br.com.dinobri.service..")
            .or()
            .resideInAnyPackage("br.com.dinobri.repository..")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage("..br.com.dinobri.web..")
            .because("Services and repositories should not depend on web layer")
            .check(importedClasses);
    }
}
