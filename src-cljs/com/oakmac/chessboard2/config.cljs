(ns com.oakmac.chessboard2.config
  (:require
    [com.oakmac.chessboard2.constants :refer [animate-speed-strings animate-speed-strings->times start-position]]
    [com.oakmac.chessboard2.util.data-transforms :refer [clj->js-map js-map->clj]]
    [com.oakmac.chessboard2.util.fen :refer [fen->position position->fen valid-fen?]]
    [com.oakmac.chessboard2.util.predicates :refer [fen-string?
                                                    map-string?
                                                    start-string?
                                                    valid-js-position-map?
                                                    valid-js-position-object?
                                                    valid-move-string?
                                                    valid-position?
                                                    valid-square?]]
    [com.oakmac.chessboard2.util.string :refer [lower-case-if-string safe-lower-case]]))

(defn valid-orientation?
  [o]
  (or (= o "white")
      (= o "black")))

(defn set-default-orientation
  [o]
  (if (= (safe-lower-case o) "black")
    "black"
    "white"))

(def config-props
  {:draggable
   {:default-val false
    :valid-fn boolean?}

   :onDragStart
   {:valid-fn fn?}

   :onDrop
   {:valid-fn fn?}

   :onSnapEnd
   {:valid-fn fn?}

   :onTouchSquare
   {:valid-fn fn?}

   :orientation
   {:default-val "white"
    :valid-fn valid-orientation?}

   :position
   {:default-val {}
    :valid-fn valid-position?}

   :touchMove
   {:default-val false
    :valid-fn boolean?}})

(def default-config
  "Create a default config map based on config-props"
  (reduce
   (fn [acc [config-key {:keys [default-val]}]]
     (assoc acc config-key default-val))
   {}
   config-props))

(def valid-config-keys
  (set (keys config-props)))

;; TODO: Good candidate for unit tests
(defn merge-config
  [their-config]
  (reduce
   (fn [new-config [config-key {:keys [default-val valid-fn]}]]
     (let [their-val (get their-config config-key)
           valid? (valid-fn their-val)]
       (if valid?
         (assoc new-config config-key their-val)
         (assoc new-config config-key default-val))))
   {}
   config-props))