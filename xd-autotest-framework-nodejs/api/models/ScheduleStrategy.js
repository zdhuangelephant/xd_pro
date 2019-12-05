/**
 * Schedule_Strategy.js
 *
 * @description :: TODO: You might write a short summary of how this model works and what it represents here.
 * @docs        :: http://sailsjs.org/documentation/concepts/models-and-orm/models
 */

module.exports = {
  connection: 'apiDatabase',
  tableName: 'xd_autotest_schedule_strategy',
  autoPK:true,
  attributes: {
    id: {
      type: 'string',
      required: false,
      primaryKey: true
    },
    uniqID:{
      type:'string',
      required:true
    },
    schedule_time: {
      type: 'string',
      required: false
    },
    day_of_week: {
      type: 'array',
      required: false,
      defaultsTo: [0]
    },
    schedule_period: {
      type: 'string',
      enum: ['-', 'daily', 'weekly'],
      required: false,
      defaultsTo: '-'
    },
    schedule_desc: {
      type: 'string',
      required: true
    }
  }
};

