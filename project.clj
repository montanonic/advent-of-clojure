(defproject advent-of-clojure "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [com.rpl/specter "1.1.1"]
                 [org.clojure/tools.trace "0.7.9"]
                 [cheshire "5.8.0"]
                 [org.clojure/core.logic "0.8.11"]
                 [org.clojure/math.combinatorics "0.1.4"]
                 [org.clojure/tools.trace "0.7.9"]
                 [net.mikera/core.matrix "0.62.0"]]
  :main ^:skip-aot advent-of-clojure.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
