const functions = require('firebase-functions');
const firebase_tools = require('firebase-tools');
var admin = require('firebase-admin');
const db = admin.firestore();

// -- Triggers for Posts Actions
exports.listenToPostsCreates = functions
  .region('asia-east2')
  .firestore.document('PostsCollection/{postID}')
  .onCreate((snap, context) => {
    
    // -- created document
    var createdDocument = snap.data();

    // -- get Friends of the author
    db.collection('FriendsCollection/'+createdDocument.authorUID+'/Friends').get()
      .then(querySnapshot=>{

        // Get a new write batch
        const batch = db.batch();
        // -- first, add it to the author Feeds
        var docRef = db.doc('FeedsCollection/'+createdDocument.authorUID+'/Posts/'+context.params.postID);
        batch.set(docRef, createdDocument);
        // -- then add to author friends feeds
        querySnapshot.forEach(docSnapshot =>{
          const ref = db.doc('FeedsCollection/'+docSnapshot.id+'/Posts/'+context.params.postID);
          console.log("id is: "+docSnapshot.id);
          batch.set(ref, createdDocument);
        });
        batch.commit();
        return null;
      }).catch(error=>{
        console.log(error);
      });
    return null;
});

exports.listenToPostsUpdates = functions
  .region('asia-east2')
  .firestore.document('PostsCollection/{postID}')
  .onUpdate((change, context) => {
    // -- document changed
    var updatedDocument = change.after.data();
    return db.collection('FriendsCollection/'+updatedDocument.authorUID+'/Friends').get()
      .then(querySnapshot=>{

        // -- loop thru friendsList
        // Get a new write batch
        const batch = db.batch();
        // -- first, update the author Posts
        var docRef = db.doc('FeedsCollection/'+updatedDocument.authorUID+'/Posts/'+context.params.postID);
        batch.update(docRef, updatedDocument);
        // -- then update the author friends feeds
        querySnapshot.forEach(docSnapshot =>{
          const ref = db.doc('FeedsCollection/'+docSnapshot.id+'/Posts/'+context.params.postID);
          batch.update(ref, updatedDocument);
        });
        batch.commit();
        return null;
      }).catch(error=>{
        console.log(error);
    });
});