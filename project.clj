(defproject district0x/district-ui-server-config "1.0.0"
  :description "district UI module for loading config from a server"
  :url "https://github.com/district0x/district-ui-server-config"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[cljs-ajax "0.7.2"]
                 [day8.re-frame/http-fx "0.1.4"]
                 [district0x/re-frame-interval-fx "1.0.2"]
                 [district0x/re-frame-spec-interceptors "1.0.1"]
                 [mount "0.1.11"]
                 [org.clojure/clojurescript "1.9.946"]
                 [re-frame "0.10.2"]]

  :doo {:paths {:karma "./node_modules/karma/bin/karma"}}

  :npm {:devDependencies [[karma "1.7.1"]
                          [karma-chrome-launcher "2.2.0"]
                          [karma-cli "1.0.1"]
                          [karma-cljs-test "0.1.0"]]}

  :profiles {:dev {:dependencies [[org.clojure/clojure "1.8.0"]
                                  [com.cemerick/piggieback "0.2.2"]
                                  [org.clojure/tools.nrepl "0.2.13"]
                                  [day8.re-frame/test "0.1.5"]]
                   :plugins [[lein-cljsbuild "1.1.7"]
                             [lein-doo "0.1.8"]
                             [lein-npm "0.6.2"]]}}

  :cljsbuild {:builds [{:id "tests"
                        :source-paths ["src" "test"]
                        :compiler {:output-to "tests-output/tests.js"
                                   :output-dir "tests-output"
                                   :main "tests.runner"
                                   :optimizations :none}}]})
