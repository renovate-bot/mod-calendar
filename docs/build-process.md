# Build Process

This module is built using Apache Maven.

The build steps are defined in `pom.xml` and the entire build process is initiated through a simple
`mvn -e install`.

## Maven Script

As of now, this is largely based on other existing modules' `pom.xml`. There appear to be a few
efforts to standardize versioning of the sub-components (ref FOLSPRINGB-8, FOLSPRINGB-3), however,
none have produced results.

The current dependencies are:

- `org.folio.folio-spring-base`, for the base FOLIO framework;
- `org.springframework.boot`, for the Spring framework itself;
- `org.projectlombok.lombok`, a IDE assistance tool for debugging;
- `org.postgresql`, to provide a Postgres interface;
- `org.mapstruct`, a tool to move between object models (included in others, but not currently
  used);
- `maven-surefire-plugin` to integrate testing;
- `maven-release-plugin` to add scripts for releasing new versions;
- `org.codehaus.mojo` for better Maven scripting; and
- `com.coderplus.maven.plugins` for the same reason.

## Module Descriptor

This is used by Okapi to get information about the module.

### `provides`

The main block (besides the meta `_tenant` present in all module) is the `mod-calendar` block
(`@artifactId@`). This block contains the schema of what API calls are allowed and all the
information about them.

### `permissionSets`

This defines the permissions applicable to the module. These should not be used directly and instead
inherited based on UI-level permissions (declared in that `package.json`).

### `launchDescriptor`

This defines how Okapi should configure an environment in which to run the module. In our case, that
is done through a Docker image (configuration for this not yet created).

The environment variables provide credentials and database information. The needed memory is
currently just the original value and should be re-defined based on actual needs (ref
[MODCAL-55](https://issues.folio.org/projects/MODCAL/issues/MODCAL-55?filter=allopenissues)).

## Deployment Descriptor

The deployment descriptor is much more straightforward -- all this does is run the JAR produced by
Maven. `embed_postgres` may be something for testing, or with Postgres itself -- unsure at the time
of writing. This deployment descriptor will support both `port` and `http.port`, however, it seems
to be implemented outside the module's purview (likely in `folio-spring-base`).