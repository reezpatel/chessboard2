(ns com.oakmac.chessboard2.util.string
  (:require
    [clojure.string :as str]))

(defn safe-lower-case
  "lower-case s if it is a String"
  [s]
  (when (string? s)
    (str/lower-case s)))
