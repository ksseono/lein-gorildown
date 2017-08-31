# Peer Getting Started

* Example source codes from here: 
http://docs.datomic.com/peer-getting-started.html

```clojure
(ns datomic-doc.peer-getting-started
  (:require [datomic.api :as d]))
```

```clojure
;;=> nil
```


## Connecting to a database

```clojure
(def db-uri "datomic:mem://hello")
(d/create-database db-uri)

(def conn (d/connect db-uri))
```

```clojure
;;=> #'datomic-doc.peer-getting-started/conn
```


## Transacting schema

```clojure
(def movie-schema [{:db/ident :movie/title
                    :db/valueType :db.type/string
                    :db/cardinality :db.cardinality/one
                    :db/doc "The title of the movie"}

                   {:db/ident :movie/genre
                    :db/valueType :db.type/string
                    :db/cardinality :db.cardinality/one
                    :db/doc "The genre of the movie"}

                   {:db/ident :movie/release-year
                    :db/valueType :db.type/long
                    :db/cardinality :db.cardinality/one
                    :db/doc "The year the movie was release in theaters"}])
```

```clojure
;;=> #'datomic-doc.peer-getting-started/movie-schema
```


```clojure
(d/transact conn movie-schema)
```

```clojure
;;=> #object[datomic.promise$settable_future$reify__6317 0x24a18ea9 {:status :ready, :val {:db-before datomic.db.Db@16ec09cd, :db-after datomic.db.Db@c70e63, :tx-data [#datom[13194139534312 50 #inst "2017-08-27T12:49:14.141-00:00" 13194139534312 true] #datom[63 10 :movie/title 13194139534312 true] #datom[63 40 23 13194139534312 true] #datom[63 41 35 13194139534312 true] #datom[63 62 "The title of the movie" 13194139534312 true] #datom[64 10 :movie/genre 13194139534312 true] #datom[64 40 23 13194139534312 true] #datom[64 41 35 13194139534312 true] #datom[64 62 "The genre of the movie" 13194139534312 true] #datom[65 10 :movie/release-year 13194139534312 true] #datom[65 40 22 13194139534312 true] #datom[65 41 35 13194139534312 true] #datom[65 62 "The year the movie was release in theaters" 13194139534312 true] #datom[0 13 65 13194139534312 true] #datom[0 13 64 13194139534312 true] #datom[0 13 63 13194139534312 true]], :tempids {-9223301668109598144 63, -9223301668109598143 64, -9223301668109598142 65}}}]
```


## Transacting data

```clojure
(def first-movies [{:movie/title "The Goonies"
                    :movie/genre "action/adventure"
                    :movie/release-year 1985}

                   {:movie/title "Commando"
                    :movie/genre "action/adventure"
                    :movie/release-year 1985}

                   {:movie/title "Repo Man"
                    :movie/genre "punk dystopia"
                    :movie/release-year 1984}])
```

```clojure
;;=> #'datomic-doc.peer-getting-started/first-movies
```


```clojure
(d/transact conn first-movies)
```

```clojure
;;=> #object[datomic.promise$settable_future$reify__6317 0x2598ccdc {:status :ready, :val {:db-before datomic.db.Db@c70e63, :db-after datomic.db.Db@b152a581, :tx-data [#datom[13194139534313 50 #inst "2017-08-27T12:49:18.842-00:00" 13194139534313 true] #datom[17592186045418 63 "The Goonies" 13194139534313 true] #datom[17592186045418 64 "action/adventure" 13194139534313 true] #datom[17592186045418 65 1985 13194139534313 true] #datom[17592186045419 63 "Commando" 13194139534313 true] #datom[17592186045419 64 "action/adventure" 13194139534313 true] #datom[17592186045419 65 1985 13194139534313 true] #datom[17592186045420 63 "Repo Man" 13194139534313 true] #datom[17592186045420 64 "punk dystopia" 13194139534313 true] #datom[17592186045420 65 1984 13194139534313 true]], :tempids {-9223301668109598141 17592186045418, -9223301668109598140 17592186045419, -9223301668109598139 17592186045420}}}]
```


## Query data

* Retrieve the current database value and store it in a var
* 'db' is a snaptiona as of a point in time: immutability

```clojure
(def db (d/db conn)) 
```

```clojure
;;=> #'datomic-doc.peer-getting-started/db
```


* Find entities with a :movie/title

```clojure
(def all-movies-q '[:find ?e :where [?e :movie/title]])
(d/q all-movies-q db)
```

```clojure
;;=> #{[17592186045418] [17592186045419] [17592186045420]}
```


* Find all the titles of all the movies

```clojure
(def all-titles-q '[:find ?movie-title :where [_ :movie/title ?movie-title]])
(d/q all-titles-q db)
```

```clojure
;;=> #{["Commando"] ["The Goonies"] ["Repo Man"]}
```


* Find the titles of movies released in 1985

```clojure
(def titles-from-1985 '[:find ?title :where [?e :movie/title ?title]
                                            [?e :movie/release-year 1985]])
(d/q titles-from-1985 db)
```

```clojure
;;=> #{["Commando"] ["The Goonies"]}
```


* Return all the attributes for each movies release in 1985

```clojure
(def all-data-from-1985 '[:find ?e ?title ?year ?genre
                          :where [?e :movie/title ?title]
                                 [?e :movie/release-year ?year]
                                 [?e :movie/genre ?genre]
                                 [?e :movie/release-year 1985]])
(d/q all-data-from-1985 db)
```

```clojure
;;=> #{[17592186045419 "Commando" 1985 "action/adventure"] [17592186045418 "The Goonies" 1985 "action/adventure"]}
```


## History of values in the database

* Bind the entity id of Commando to a local variable

```clojure
(def command-id (ffirst (d/q '[:find ?e :where [?e :movie/title "Commando"]] db)))
```

```clojure
;;=> #'datomic-doc.peer-getting-started/command-id
```


* Issue a trasaction to change the data

```clojure
(d/transact conn [{:db/id command-id :movie/genre "future governor"}])
```

```clojure
;;=> #object[datomic.promise$settable_future$reify__6317 0x2016887c {:status :ready, :val {:db-before datomic.db.Db@162a2a74, :db-after datomic.db.Db@98781bb1, :tx-data [#datom[13194139534319 50 #inst "2017-08-27T12:52:27.756-00:00" 13194139534319 true]], :tempids {}}}]
```


* Get a current value of the database

```clojure
(def db (d/db conn))
```

```clojure
;;=> #'datomic-doc.peer-getting-started/db
```


* Verify that Commando has been updated

```clojure
(d/q all-data-from-1985 db)
```

```clojure
;;=> #{[17592186045419 "Commando" 1985 "future governor"] [17592186045418 "The Goonies" 1985 "action/adventure"]}
```


* Take a database value of a 'db-before'

```clojure
(def old-db (d/as-of db 13194139534313))
```

```clojure
;;=> #'datomic-doc.peer-getting-started/old-db
```


* Issue the same query against 'db-before'

```clojure
(d/q all-data-from-1985 old-db)
```

```clojure
;;=> #{[17592186045419 "Commando" 1985 "action/adventure"] [17592186045418 "The Goonies" 1985 "action/adventure"]}
```


* Call history on the existing database value

```clojure
(def hdb (d/history db))
```

```clojure
;;=> #'datomic-doc.peer-getting-started/hdb
```


* Query against hdb

```clojure
(d/q '[:find ?genre
       :where [?e :movie/title "Commando"]
              [?e :movie/genre ?genre]] hdb)
```

```clojure
;;=> #{["action/adventure"] ["future governor"]}
```

