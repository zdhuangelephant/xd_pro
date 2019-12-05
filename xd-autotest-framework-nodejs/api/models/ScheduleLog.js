/**
 * ScheduleLog.js
 *
 * @description :: TODO: You might write a short summary of how this model works and what it represents here.
 * @docs        :: http://sailsjs.org/documentation/concepts/models-and-orm/models
 */

module.exports = {
  connection: 'apiDatabase',
  tableName: 'xd_autotest_schedule_log',
  autoPK:true,
  attributes: {
    id:{
      type:'string',
      required:false,
      primaryKey:true
    },
    log_id:{
      type:'string',
      required:false,
      unique:true
    },
    sc_id:{
      type:'string',
      required:false,
    },
    log_desc:{
      type:'string',
      required:false,
    },
    log_html:{
      type:'string',
      required:false,
    },
  }
};
