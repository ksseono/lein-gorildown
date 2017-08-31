# lein-gorildown

A Leiningen plugin to convert all the [Gorilla REPL](http://gorilla-repl.org/) files in the current project or the specified directory to the raw Markdown files, based on [ksseono/gorildown](https://github.com/ksseono/gorildown) project.

Gorilla REPL provide [Jupyter](http://jupyter.org/) like web UI and save the notebooks in the Clojure files with some comments following its own notations to render notebook styles later. 

But it's not convinient when you want to share others the notebooks as they are and also it's not a good way to force others to use Gorilla REPL only to look throught the content. 

`lein-gorildown` convert the Gorilla REPL files(notebooks) in your project into the raw Markdown files so that you can easily share them. You can check the sample results [here](https://github.com/ksseono/lein-gorildown/tree/master/examples).

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
