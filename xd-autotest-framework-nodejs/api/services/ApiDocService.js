/**
 * Created by zhaodan on 2017/7/17.
 */

module.exports = {
    findAllDoc: function (callback) {
        APIdoc.find({state:1}).exec(function(err,records){
            if (!err) {
                callback(records);
            } else {
                console.log("find all APIdoc records failure!");
                console.log(err);
                callback(null);
            }
        });
    },

    findDocWithItem: function (uniqId, callback) {
        APIdoc.findOne({uniqID: uniqId, state:1}).populate('APIdoc_items',{sort:'createdAt ASC'}).exec(function(err, record){
            if (!err) {
                callback(record);
            } else {
                console.log("find APIdoc with item failure!");
                console.log(err);
                callback(null);
            }
        })
    },
    
    deleteDoc: function (uniqId, callback) {
        APIdoc.update({uniqID: uniqId},{state:0}).exec(function (err, record) {
            if (!err) {
                callback(record);
            } else {
                console.log("delete APIdoc %s failure!", uniqId);
                console.log(err);
                callback(null);
            }
        })
    }
}