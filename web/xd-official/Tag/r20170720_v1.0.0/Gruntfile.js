module.exports = function(grunt) {

  // Project configuration.
  grunt.initConfig({
    pkg: grunt.file.readJSON('package.json'),

    clean: {  // Grunt任务开始前的清理工作
      files: ['build/*']
    },

    //构建html js
    includereplace: {
      html: {
        options: {
        },
        src: 'src/*.html',
        dest: 'public/'
      },
      js: {
        options: {
        },
        src: 'src/js/*.js',
        dest: 'public/js/'
      }
    },

    concat: {
      options: {
        separator: ';',
        stripBanners: true
      },
      dist: {
        src: [
          "src/js/*.js"
        ],
        dest: "public/js/default.js"
      }
    },




    //把css源码合并、最小化
    cssmin: {
      options: {
        banner: '/*! <%= pkg.name %> <%= grunt.template.today("yyyy-mm-dd") %> /\n'
      },
      compress: {
        files: {
          'public/css/main.min.css': [
            "src/css/*.css"
          ]
        }
      }
    },

    //监控文件变化，执行html css构建
    watch: {
      files: ['src/*'],
      tasks: ['includereplace','cssmin']
    },

    //开启一个简易服务器
    connect: {
      server: {
        options: {
          port: 3000,
          base: './public/'
        }
      }
    },

    //自动打开浏览器
    open: {
      kitchen: {
        path: 'http://localhost:3000/'
      }
    }
  });

  grunt.loadNpmTasks('grunt-contrib-clean');


  grunt.loadNpmTasks('grunt-include-replace');
  grunt.loadNpmTasks('grunt-contrib-uglify');
  grunt.loadNpmTasks('grunt-contrib-concat');
  grunt.loadNpmTasks('grunt-contrib-cssmin');

  grunt.loadNpmTasks('grunt-contrib-jshint');

  grunt.loadNpmTasks('grunt-contrib-watch');
  grunt.loadNpmTasks('grunt-contrib-connect');
  grunt.loadNpmTasks('grunt-open');

  grunt.registerTask('package', ['clean','includereplace']);

  grunt.registerTask('default', [
    'clean',
    'includereplace',
    'cssmin',
    'watch'
  ]);

};