(defproject lein-gorildown "0.1.0-SNAPSHOT"
  :description "A Leiningen plugin to convert all the Gorilla REPL files to raw Markdown files."
  :url "http://github.com/ksseono/lein-gorildown"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [[gorildown "0.1.0-SNAPSHOT"]]
  :eval-in-leiningen true

  :lein-release {:deploy-via :clojars})
