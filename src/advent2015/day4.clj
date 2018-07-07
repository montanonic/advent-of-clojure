(ns advent2015.day4)

(require 'crypto '[clojure.string :as string])

(defn md5 [s]
  (.. (crypto/Hash "md5") (update s) (digest "hex")))

(def secret-key "bgvyzdsv")

(defn solve [num-zeroes]
  (loop [n 0]
    (if
      (=
        (repeat num-zeroes "0")
        (take num-zeroes (md5 (string/join [secret-key (str n)]))))
      n
      (recur (inc n)))))
