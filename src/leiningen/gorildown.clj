(ns leiningen.gorildown
  (:require [gorildown.core :as g]
            [clojure.string :as s]
            [clojure.java.io :as io]
            [leiningen.classpath :as l]))

(defn gorilla?
  [file]
  (with-open [rdr (io/reader file)]
    (s/starts-with? (first (line-seq rdr)) ";; gorilla-repl.fileformat")))

(defn filter-gorilla-files
  [dir]
  (->> (io/file dir)
       file-seq
       (filter #(s/includes? % ".clj")) ;; filter clojure files
       (filter #(not (s/includes? % ".#"))) ;; exclude emacs temp files
       (filter #(gorilla? %))
       (map str)))

(defn write-markdown
  [f]
  (let [nb (slurp f)
        fname (str f ".md")]
    (spit fname (g/gorilla-to-markdown nb))
    (println "write.." fname)))

(defn gorildown
  "Convert all the Gorilla REPL files in the project to raw Markdown files. If basedir is specified then use it. If not, use 'src' directory in the current project as default."
  [project & args]
  (let [basedir (or (first args)
                    (-> (with-out-str (l/classpath project))
                        (s/split #":")
                        (->> (filter #(s/ends-with? % "/src")))
                        first))]
    (println "basedir:" (str basedir))
    (doall (map write-markdown (filter-gorilla-files basedir)))))
