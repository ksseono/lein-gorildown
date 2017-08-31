# lein-gorildown

A Leiningen plugin to convert all the Gorilla REPL files to raw Markdown files, based on [ksseono/gorildown](https://github.com/ksseono/gorildown) project.

## Dependencies

    [gorildown "0.1.0-SNAPSHOT"]

## Usage

#### Settings

Put this plugin information into the `:plugins` vector of your `project.clj` or `~/.lein/profiles.clj`.

```clojure
[lein-gorildown "0.1.0-SNAPSHOT"]
```

#### Excution

```Shell
# Apply to the current project Gorilla REPL files
$ lein gorildown

# Or you can specify the target directory you want
$ lein gorildown /your/target/path/
```

#### Output

  If you have `src/gorilla/nb1.clj` and `src/gorilla/nb2.clj`, 
  then `src/gorilla/nb1.clj.md` and `src/gorilla/nb2.clj.md` will be generated.

## License

Copyright © 2017 [Seonho Kim](http://seonho.kim)

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
