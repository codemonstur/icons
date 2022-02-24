# Intellij Icons

This repo contains all the icons from intellij IDEA.
The project imports this dependency:

    <dependency>
        <groupId>com.jetbrains.intellij.platform</groupId>
        <artifactId>icons</artifactId>
        <version>213.6777.50</version>
    </dependency>

But it rearranges the icons into different resource directories.
All icons are under an /icons/ dir.
Within that directory the icons are organized according to their size and lighting setting.

    {width}x{height}-{lighting}

The lighting setting is either 'light' or 'dark'.

This directory structure allows you to more easily find a suitable icon for your needs.

All the icons are owned by JetBrains and released under the Apache 2.0 license.
