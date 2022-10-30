import { FormEventHandler } from "react";

type Props = {
    onSubmit: FormEventHandler,
}

export default function SendMessageForm({ onSubmit }: Props) {
    return (
        <form method="post" onSubmit={onSubmit} className="mt-3">
            <div className="row g-3 align-items-center">
                <div className="col-auto">
                    <label htmlFor="messageText" className="col-form-label">
                        <strong>TOOD: john.doe:</strong>
                    </label>
                </div>
                <div className="col">
                    <input type="text" name="text" placeholder="Say something..." className="form-control" id="messageText" required autoFocus/>
                </div>
                <div className="col-auto">
                    <button type="submit" className="btn btn-primary">Send</button>
                </div>
            </div>
        </form>
    );
}