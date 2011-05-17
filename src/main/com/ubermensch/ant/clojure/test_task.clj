(ns com.ubermensch.ant.clojure.test-task
  (:gen-class
     :name com.ubermensch.ant.clojure.TestTask
     :extends com.ubermensch.ant.clojure.base_task
     :init init-task
     :state state)
  (:use clojure.contrib.test-is)
  (:require [com.ubermensch.ant.clojure.base-task :as base]
            [clojure.set :as set]))

(defn- -init-task [] (base/initial-state))

(defn -execute [this]
  (base/with-classloader
    (let [namespace-names (set (set/union
                            (map #(.name %) (:namespaces @state))
                            (base/filesets->namespaces (:filesets @state))))
          namespaces (map symbol namespace-names)]
      (apply require namespaces)
      (let [summary (apply run-tests namespaces)
            failures (+ (:fail summary) (:error summary))]
        (if (pos? failures)
          (throw (org.apache.tools.ant.BuildException.
                   (str "tests unsuccessful: " failures))))))))
