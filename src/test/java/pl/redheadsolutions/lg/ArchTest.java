package pl.redheadsolutions.lg;

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
            .importPackages("pl.redheadsolutions.lg");

        noClasses()
            .that()
            .resideInAnyPackage("pl.redheadsolutions.lg.service..")
            .or()
            .resideInAnyPackage("pl.redheadsolutions.lg.repository..")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage("..pl.redheadsolutions.lg.web..")
            .because("Services and repositories should not depend on web layer")
            .check(importedClasses);
    }
}
