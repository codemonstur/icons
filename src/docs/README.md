# icons

The IntelliJ IDEA platform icons, **re-organized for easy lookup**.

The original `com.jetbrains.intellij.platform:icons` jar stores every icon under a
flat `META-INF`-style layout that is hard to navigate by hand. This project
repackages the same artwork under a predictable path scheme:

```
/icons/{width}x{height}-{lighting}/name.svg
```

where `lighting` ∈ `{light, dark}`. For example:

```
/icons/16x16-dark/folder.svg
/icons/24x24-light/module.svg
```

## Contents

The resource buckets under `src/main/resources` are the actual product of this
library. The Java sources are only a **regeneration tool**:

- `ExtractIcons` — downloads the JetBrains icons jar from
  `jetbrains.com/intellij-repository`, verifies it against `maven-metadata.xml`,
  and reorganizes every entry into the bucket structure above.
- `model/IconBucket`, `model/ImageType` (svg/png), `model/MavenMetadata` —
  parsing and target-layout modelling.
- `util/IO`, `util.Logging` — helpers for the extraction run.

## Dependency

```xml
<dependency>
  <groupId>com.github.codemonstur</groupId>
  <artifactId>icons</artifactId>
  <version>213.6777.52</version>
</dependency>
```

The version tracks the upstream IntelliJ icons artifact version.

## Building

```sh
make build
```

## License

Apache License 2.0 — the icon artwork is owned by **JetBrains**. This project
only repackages it under a friendlier directory layout.
