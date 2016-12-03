/**
 * This package contains all the task associated with a Janus server. This tasks
 * run on a respective thread. All inherits of {@link JanusTask}. The
 * {@link ConnectTask} dispatch the client and assigned him to a
 * {@link TreatmentTask} that execute the commands receive.
 */
package com.seikomi.janus.net.tasks;